package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.AuthenticationRequest;
import vn.theonestudio.surf.dto.request.CreateUserRequest;
import vn.theonestudio.surf.dto.response.AuthenticationResponse;
import vn.theonestudio.surf.dto.response.UserResponse;
import vn.theonestudio.surf.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/user", name = "User API")
@AllArgsConstructor
public class UserEndpoint {

    ApiExecutorService apiExecutorService;

    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUserEndpoint(@RequestBody CreateUserRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(userRepository.createUser(request)));
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authEndpoint(@RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(userRepository.auth(request)));
    }

    @GetMapping(path = "/{teamId}")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersInTeam(@PathVariable UUID teamId, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(userRepository.getAllUsersInTeam(teamId)));
    }

}
