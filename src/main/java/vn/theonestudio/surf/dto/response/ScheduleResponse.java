package vn.theonestudio.surf.dto.response;

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
public class ScheduleResponse {

    UUID id;

    Instant date;

    String name;

    List<UserResponse> attendants;

    List<String> links;

    String platform;

    String url;

    UserResponse createdBy;

    Instant createdAt;

    UUID teamId;

}
