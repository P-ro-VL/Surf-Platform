package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.CreateScheduleRequest;
import vn.theonestudio.surf.dto.response.ScheduleResponse;
import vn.theonestudio.surf.repository.ScheduleRepository;

@RestController
@RequestMapping(path = "/v1/schedule", name = "Schedule API")
@AllArgsConstructor
public class ScheduleEndpoint {

    ApiExecutorService apiExecutorService;

    ScheduleRepository scheduleRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createScheduleEndpoint(@RequestBody CreateScheduleRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(scheduleRepository.createSchedule(request)));
    }

}
