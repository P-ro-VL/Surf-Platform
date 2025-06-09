package vn.theonestudio.surf.repository;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.TicketDataSource;
import vn.theonestudio.surf.dto.request.CreateTicketRequest;
import vn.theonestudio.surf.dto.request.UpdateTicketRequest;
import vn.theonestudio.surf.dto.response.TicketResponse;
import vn.theonestudio.surf.enumeration.TicketStatus;
import vn.theonestudio.surf.model.TicketModel;

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class TicketRepository {

    TicketDataSource ticketDataSource;

    UserRepository userRepository;
    FixVersionRepository fixVersionRepository;
    TeamRepository teamRepository;

    // Utility methods
    public String generateNextTicketId(@Nullable String lastTicketId) {
        if(lastTicketId != null) {
            Optional<TicketModel> ticketOpt = ticketDataSource.findByTicketId(lastTicketId);
            if(ticketOpt.isEmpty()) return lastTicketId;
        }

        int ticketLength = (int) (ticketDataSource.count() + 1);
        String ticketId = "TICKET-" + ticketLength;

        return generateNextTicketId(ticketId);
    }

    public TicketResponse parseResponse(UUID ticketId) {
        Optional<TicketModel> ticketOpt = ticketDataSource.findById(ticketId);
        return parseResponse(ticketOpt.orElse(null));
    }

    public TicketResponse parseResponse(TicketModel ticketModel) {
        if(ticketModel == null) return null;

        Optional<TicketModel> parentOpt = ticketDataSource.findById(Objects.requireNonNullElse(ticketModel.getParentId(), UUID.randomUUID()));

        return TicketResponse.builder()
                .uuid(ticketModel.getUuid())
                .ticketId(ticketModel.getTicketId())
                .ticketName(ticketModel.getTicketName())
                .description(ticketModel.getDescription())
                .links(ticketModel.getLinks())
                .status(ticketModel.getStatus())
                .type(ticketModel.getType())
                .parent(
                        parseResponse(parentOpt.orElse(null))
                )
                .assignee(
                        userRepository.parseResponse(ticketModel.getAssigneeId())
                )
                .labels(ticketModel.getLabels())
                .storyPoints(ticketModel.getStoryPoints())
                .fixVersion(
                        fixVersionRepository.parseResponse(
                                ticketModel.getFixVersionId()
                        )
                )
                .dueDate(ticketModel.getDueDate())
                .createdAt(ticketModel.getCreatedAt())
                .createdBy(
                        userRepository.parseResponse(ticketModel.getCreatedBy())
                )
                .updatedAt(ticketModel.getUpdatedAt())
                .team(
                        teamRepository.parseResponse(ticketModel.getTeamId())
                )
                .subtasks(
                        ticketModel.getSubtasks()
                                .stream()
                                .map(this::parseResponse)
                                .toList()
                )
                .build();
    }

    // Action methods
    public TicketResponse createTicket(CreateTicketRequest request) {
        String rawCurrentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        TicketModel ticket = TicketModel.builder()
                .uuid(UUID.randomUUID())
                .ticketId(generateNextTicketId(null))
                .ticketName(request.getName())
                .description(request.getDescription())
                .links(new ArrayList<>())
                .status(TicketStatus.BACKLOG.name())
                .type(request.getType())
                .parentId(request.getParentId())
                .assigneeId(request.getAssigneeId())
                .labels(request.getLabels())
                .storyPoints(request.getStoryPoints())
                .fixVersionId(request.getFixVersionId())
                .dueDate(request.getDueDate())
                .createdAt(Instant.now())
                .createdBy(UUID.fromString(rawCurrentUserId))
                .updatedAt(Instant.now())
                .teamId(request.getTeamId())
                .subtasks(new ArrayList<>())
                .build();

        ticketDataSource.save(ticket);

        return parseResponse(ticket);
    }

    public TicketResponse updateTicket(UUID ticketId, UpdateTicketRequest request) {
        Optional<TicketModel> ticketOpt = ticketDataSource.findById(ticketId);
        if(ticketOpt.isEmpty()) return null;

        TicketModel ticket = ticketOpt.get();

        if(request.getTicketName() != null) {
            ticket.setTicketName(request.getTicketName());
        }

        if(request.getDescription() != null) {
            ticket.setDescription(request.getDescription());
        }

        if(request.getLinks() != null) {
            ticket.setLinks(request.getLinks());
        }

        if(request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
        }

        if(request.getAssigneeId() != null) {
            ticket.setAssigneeId(request.getAssigneeId());
        }

        if(request.getLabels() != null) {
            ticket.setLabels(request.getLabels());
        }

        if(request.getStoryPoints() != null) {
            ticket.setStoryPoints(request.getStoryPoints());
        }

        if(request.getFixVersionId() != null) {
            ticket.setFixVersionId(request.getFixVersionId());
        }

        if(request.getDueDate() != null) {
            ticket.setDueDate(request.getDueDate());
        }

        if(request.getSubtasks() != null) {
            ticket.setSubtasks(request.getSubtasks());
        }

        ticketDataSource.save(ticket);

        return parseResponse(ticket);
    }

    public boolean deleteTicket(UUID ticketId) {
        Optional<TicketModel> ticketOpt = ticketDataSource.findById(ticketId);
        if(ticketOpt.isEmpty()) return false;
        ticketDataSource.deleteById(ticketId);
        return true;
    }

    public List<TicketResponse> getAllTickets(UUID teamId) {
        return ticketDataSource.findAll()
                .stream()
                .filter((ticket) -> ticket.getTeamId().equals(teamId))
                .map(this::parseResponse)
                .toList();
    }

}
