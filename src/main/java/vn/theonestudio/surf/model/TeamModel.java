package vn.theonestudio.surf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("teams")
public class TeamModel {

    @Id
    UUID id;

    String teamName;

    UUID createdBy;

    Instant createdAt;

}
