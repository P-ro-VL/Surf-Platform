package vn.theonestudio.surf.repository;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.TeamDataSource;
import vn.theonestudio.surf.dto.response.TeamResponse;
import vn.theonestudio.surf.model.TeamModel;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeamRepository {

    TeamDataSource teamDataSource;

    FixVersionRepository fixVersionRepository;
    UserRepository userRepository;
    TeamRepository teamRepository;

    // Utility methods
    public TeamResponse parseResponse(UUID teamId) {
        Optional<TeamModel> teamOpt = teamDataSource.findById(teamId);
        return parseResponse(teamOpt.orElse(null));
    }

    public TeamResponse parseResponse(@Nullable TeamModel teamModel) {
        if(teamModel == null) return null;

        String rawCurrentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        return TeamResponse.builder()
                .id(teamModel.getId())
                .teamName(teamModel.getTeamName())
                .createdAt(Instant.now())
                .createdBy(userRepository.parseResponse(UUID.fromString(rawCurrentUserId)))
                .build();
    }

}
