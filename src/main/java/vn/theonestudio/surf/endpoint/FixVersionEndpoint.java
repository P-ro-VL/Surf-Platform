package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.CreateFixVersionRequest;
import vn.theonestudio.surf.dto.request.UpdateFixVersionRequest;
import vn.theonestudio.surf.dto.response.FixVersionResponse;
import vn.theonestudio.surf.repository.FixVersionRepository;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/fix-version", name = "Fix Version API")
@AllArgsConstructor
public class FixVersionEndpoint {

    ApiExecutorService apiExecutorService;

    FixVersionRepository fixVersionRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<FixVersionResponse>> createFixVersionEndpoint(@RequestBody CreateFixVersionRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(fixVersionRepository.createFixVersion(request)));
    }

    @PutMapping(path = "/{fixVersionId}")
    public ResponseEntity<ApiResponse<FixVersionResponse>> updateFixVersionEndpoint(@PathVariable UUID fixVersionId, @RequestBody UpdateFixVersionRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(fixVersionRepository.updateFixVersion(fixVersionId, request)));
    }
}
