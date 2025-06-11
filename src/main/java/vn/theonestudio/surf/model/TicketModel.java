package vn.theonestudio.surf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("tickets")
public class TicketModel {

    @Id
    UUID uuid;

    String ticketId;

    String ticketName;

    String description;

    List<String> links;

    String status;

    String type;

    UUID parentId;

    UUID assigneeId;

    List<String> labels;

    Integer storyPoints;

    UUID fixVersionId;

    Instant dueDate;

    Instant createdAt;

    UUID createdBy;

    Instant updatedAt;

    UUID teamId;

    List<TicketRelation> relations;

    @Data
    @Builder
    public static class TicketRelation {
        String relationType;

        UUID ticketUuid;
    }
}
