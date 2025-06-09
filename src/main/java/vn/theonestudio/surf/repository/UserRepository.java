package vn.theonestudio.surf.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.datasource.UserDataSource;
import vn.theonestudio.surf.dto.response.UserResponse;
import vn.theonestudio.surf.model.UserModel;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserRepository {

    UserDataSource userDataSource;

    // Utility methods
    public UserResponse parseResponse(UUID userId) {
        Optional<UserModel> userModel = userDataSource.findById(userId);
        return parseResponse(userModel.get());
    }

    public UserResponse parseResponse(UserModel userModel) {
        return UserResponse.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .employeeId(userModel.getEmployeeId())
                .build();
    }


}
