package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.CreateScheduleRequest;
import vn.theonestudio.surf.dto.request.UpdateScheduleRequest;
import vn.theonestudio.surf.dto.response.ScheduleResponse;
import vn.theonestudio.surf.repository.ScheduleRepository;

import java.util.UUID;

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

    @PutMapping(path = "/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateScheduleEndpoint(@PathVariable UUID scheduleId, @RequestBody UpdateScheduleRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(scheduleRepository.updateSchedule(scheduleId, request)));
    }

    @DeleteMapping(path = "/{scheduleId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteScheduleEndpoint(@PathVariable UUID scheduleId, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(scheduleRepository.deleteSchedule(scheduleId)));
    }

}
