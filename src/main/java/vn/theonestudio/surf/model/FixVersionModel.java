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
@Document("fix_versions")
public class FixVersionModel {

    @Id
    UUID id;

    String versionName;

    Instant releaseDate;

    Instant startDate;

    String goal;

    String status;

    List<Approver> approvers;

    @Data
    @Builder
    public static class Approver {
        UUID userId;

        String approvalStatus;
    }

}


