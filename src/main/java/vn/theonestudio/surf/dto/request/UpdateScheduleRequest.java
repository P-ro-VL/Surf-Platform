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
public class UpdateScheduleRequest {

    String name;

    List<UUID> attendants;

    List<String> links;

    String platform;

    String url;

}
