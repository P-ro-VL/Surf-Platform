package vn.theonestudio.surf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.theonestudio.surf.model.FixVersionModel;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFixVersionRequest {

    String versionName;

    Instant releaseDate;

    Instant startDate;

    String goal;

    String status;

    List<UpdateFixVersionApprovalRequest> approvers;

}
