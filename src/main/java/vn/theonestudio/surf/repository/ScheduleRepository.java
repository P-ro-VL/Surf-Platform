package vn.theonestudio.surf.repository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.ScheduleDataSource;
import vn.theonestudio.surf.dto.request.CreateScheduleRequest;
import vn.theonestudio.surf.dto.request.UpdateScheduleRequest;
import vn.theonestudio.surf.dto.response.ScheduleResponse;
import vn.theonestudio.surf.model.ScheduleModel;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ScheduleRepository {

    ScheduleDataSource scheduleDataSource;

    UserRepository userRepository;

    // Utility methods
    public ScheduleResponse parseResponse(ScheduleModel scheduleModel) {
        return ScheduleResponse.builder()
                .id(scheduleModel.getId())
                .date(scheduleModel.getDate())
                .name(scheduleModel.getName())
                .attendants(
                        scheduleModel.getAttendants()
                                .stream()
                                .map((attendant) -> userRepository.parseResponse(attendant))
                                .toList()
                )
                .links(scheduleModel.getLinks())
                .platform(scheduleModel.getPlatform())
                .url(scheduleModel.getUrl())
                .createdBy(
                        userRepository.parseResponse(scheduleModel.getCreatedBy())
                )
                .teamId(scheduleModel.getTeamId())
                .createdAt(scheduleModel.getCreatedAt())
                .build();
    }

    // Action method
    public ScheduleResponse createSchedule(CreateScheduleRequest request) {
        String rawCurrentUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        ScheduleModel schedule = ScheduleModel.builder()
                .id(UUID.randomUUID())
                .date(request.getDate())
                .name(request.getName())
                .attendants(request.getAttendants())
                .platform(request.getPlatform())
                .url(request.getUrl())
                .createdBy(UUID.fromString(rawCurrentUserId))
                .createdAt(Instant.now())
                .teamId(request.getTeamId())
                .build();

        scheduleDataSource.save(schedule);

        return parseResponse(schedule);
    }

    public ScheduleResponse updateSchedule(UUID scheduleId, UpdateScheduleRequest request) {
        Optional<ScheduleModel> scheduleOpt = scheduleDataSource.findById(scheduleId);
        if(scheduleOpt.isEmpty()) return null;

        ScheduleModel schedule = scheduleOpt.get();

        if(request.getName() != null) {
            schedule.setName(request.getName());
        }

        if(request.getAttendants() != null) {
            schedule.setAttendants(request.getAttendants());
        }

        if(request.getLinks() != null) {
            schedule.setLinks(request.getLinks());
        }

        if(request.getPlatform() != null) {
            schedule.setPlatform(request.getPlatform());
        }

        if(request.getUrl() != null) {
            schedule.setUrl(request.getUrl());
        }

        scheduleDataSource.save(schedule);

        return parseResponse(schedule);
    }

}
