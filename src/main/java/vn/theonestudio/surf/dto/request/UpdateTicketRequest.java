package vn.theonestudio.surf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketRequest {

    String ticketName;

    String description;

    List<String> links;

    String status;

    UUID assigneeId;

    List<String> labels;

    Integer storyPoints;

    UUID fixVersionId;

    Instant dueDate;

    List<UUID> subtasks;

}
