package vn.theonestudio.surf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {

    UUID id;

    String teamName;

    UserResponse createdBy;

    Instant createdAt;

}
