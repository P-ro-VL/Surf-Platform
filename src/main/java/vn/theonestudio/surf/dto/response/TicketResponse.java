package vn.theonestudio.surf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.theonestudio.surf.model.TicketModel;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {

    UUID uuid;

    String ticketId;

    String ticketName;

    String description;

    List<String> links;

    String status;

    String type;

    TicketResponse parent;

    UserResponse assignee;

    List<String> labels;

    Integer storyPoints;

    FixVersionResponse fixVersion;

    Instant dueDate;

    Instant createdAt;

    UserResponse createdBy;

    Instant updatedAt;

    TeamResponse team;

    Map<String, List<UUID>> relations;

}

