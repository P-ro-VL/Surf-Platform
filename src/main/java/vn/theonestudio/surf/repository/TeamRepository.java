package vn.theonestudio.surf.repository;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.TeamDataSource;
import vn.theonestudio.surf.dto.response.TeamResponse;
import vn.theonestudio.surf.model.TeamModel;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeamRepository {

    TeamDataSource teamDataSource;

    UserRepository userRepository;

    // Utility methods
    public TeamResponse parseResponse(UUID teamId) {
        Optional<TeamModel> teamOpt = teamDataSource.findById(teamId);
        return parseResponse(teamOpt.orElse(null));
    }

    public TeamResponse parseResponse(@Nullable TeamModel teamModel) {
        if(teamModel == null) return null;

        String rawCurrentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(rawCurrentUserId.equals("anonymousUser")) rawCurrentUserId = UUID.randomUUID().toString();

        return TeamResponse.builder()
                .id(teamModel.getId())
                .teamName(teamModel.getTeamName())
                .createdAt(Instant.now())
                .createdBy(userRepository.parseResponse(UUID.fromString(rawCurrentUserId)))
                .build();
    }

    // Action methods
    public TeamResponse createTeam(String teamName) {
        String rawCurrentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        if(rawCurrentUserId.equals("anonymousUser")) rawCurrentUserId = UUID.randomUUID().toString();

        TeamModel team = TeamModel.builder()
                .id(UUID.randomUUID())
                .teamName(teamName)
                .createdAt(Instant.now())
                .createdBy(UUID.fromString(rawCurrentUserId))
                .build();
        teamDataSource.save(team);

        return parseResponse(team);
    }

    public List<TeamResponse> getAllTeams() {
        return teamDataSource.findAll().stream().map(this::parseResponse).toList();
    }

}
