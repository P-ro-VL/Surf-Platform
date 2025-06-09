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
public class CreateTicketRequest {

    String name;

    String description;

    UUID assigneeId;

    int storyPoints;

    Instant dueDate;

    List<String> labels;

    UUID fixVersionId;

    String type;

    UUID parentId;

    UUID teamId;

}
