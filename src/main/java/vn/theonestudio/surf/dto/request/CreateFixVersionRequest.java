package vn.theonestudio.surf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.theonestudio.surf.model.FixVersionModel;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFixVersionRequest {

    String versionName;

    Instant releaseDate;

    Instant startDate;

    String goal;

    String status;

    List<UUID> approverIds;

}
