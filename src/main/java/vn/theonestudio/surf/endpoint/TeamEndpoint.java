package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.CreateTeamRequest;
import vn.theonestudio.surf.dto.response.TeamResponse;
import vn.theonestudio.surf.repository.TeamRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/team", name = "Team API")
@AllArgsConstructor
public class TeamEndpoint {

    ApiExecutorService apiExecutorService;

    TeamRepository teamRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeamEndpoint(@RequestBody CreateTeamRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(teamRepository.createTeam(request.getName())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamResponse>>> getAllTeamsEndpoint(HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(teamRepository.getAllTeams()));
    }
}
