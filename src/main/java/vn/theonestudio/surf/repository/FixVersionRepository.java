package vn.theonestudio.surf.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.FixVersionDataSource;
import vn.theonestudio.surf.dto.request.CreateFixVersionRequest;
import vn.theonestudio.surf.dto.request.UpdateFixVersionRequest;
import vn.theonestudio.surf.dto.response.ApproverResponse;
import vn.theonestudio.surf.dto.response.FixVersionResponse;
import vn.theonestudio.surf.enumeration.ApprovalStatus;
import vn.theonestudio.surf.model.FixVersionModel;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FixVersionRepository {

    FixVersionDataSource fixVersionDataSource;

    UserRepository userRepository;

    // Utility methods
    public FixVersionResponse parseResponse(UUID fixVersionId) {
        if(fixVersionId == null) return null;
        Optional<FixVersionModel> fixVersionOpt = fixVersionDataSource.findById(fixVersionId);
        return parseResponse(fixVersionOpt.orElse(null));
    }

    public FixVersionResponse parseResponse(FixVersionModel fixVersionModel) {
        if(fixVersionModel == null) return null;
        return FixVersionResponse.builder()
                .id(fixVersionModel.getId())
                .versionName(fixVersionModel.getVersionName())
                .releaseDate(fixVersionModel.getReleaseDate())
                .startDate(fixVersionModel.getStartDate())
                .goal(fixVersionModel.getGoal())
                .status(fixVersionModel.getStatus())
                .approvers(
                        fixVersionModel.getApprovers()
                                .stream()
                                .map((approver) -> ApproverResponse.builder()
                                        .user(userRepository.parseResponse(approver.getUserId()))
                                        .approvalStatus(approver.getApprovalStatus())
                                        .build())
                                .toList()
                )
                .build();
    }

    // Action method
    public FixVersionResponse createFixVersion(CreateFixVersionRequest request) {
        FixVersionModel fixVersion = FixVersionModel.builder()
                .id(UUID.randomUUID())
                .versionName(request.getVersionName())
                .releaseDate(request.getReleaseDate())
                .startDate(request.getStartDate())
                .goal(request.getGoal())
                .status(request.getStatus())
                .approvers(
                        request.getApproverIds()
                                .stream()
                                .map((approver) -> FixVersionModel.Approver.builder()
                                        .userId(approver)
                                        .approvalStatus(ApprovalStatus.PENDING.name())
                                        .build())
                                .toList()
                )
                .build();

        fixVersionDataSource.save(fixVersion);

        return parseResponse(fixVersion);
    }

    public FixVersionResponse updateFixVersion(UUID fixVersionId, UpdateFixVersionRequest request) {
        Optional<FixVersionModel> fixVersionOpt = fixVersionDataSource.findById(fixVersionId);
        if(fixVersionOpt.isEmpty()) return null;

        FixVersionModel fixVersion = fixVersionOpt.get();

        if(request.getVersionName() != null) {
            fixVersion.setVersionName(request.getVersionName());
        }

        if(request.getReleaseDate() != null) {
            fixVersion.setReleaseDate(request.getReleaseDate());
        }

        if(request.getStartDate() != null) {
            fixVersion.setStartDate(request.getStartDate());
        }

        if(request.getGoal() != null) {
            fixVersion.setGoal(request.getGoal());
        }

        if(request.getStatus() != null) {
            fixVersion.setStatus(request.getStatus());
        }

        if(request.getApprovers() != null) {
            fixVersion.setApprovers(
                    request.getApprovers()
                            .stream()
                            .map(approver -> FixVersionModel.Approver.builder()
                                    .userId(approver.getId())
                                    .approvalStatus(approver.getApprovalStatus())
                                    .build())
                            .toList()
            );
        }

        fixVersionDataSource.save(fixVersion);

        return parseResponse(fixVersion);
    }

}
