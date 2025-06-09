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
@Document("schedules")
public class ScheduleModel {

    @Id
    UUID id;

    Instant date;

    String name;

    List<UUID> attendants;

    List<String> links;

    String platform;

    String url;

    UUID createdBy;

    Instant createdAt;

    UUID teamId;

}
