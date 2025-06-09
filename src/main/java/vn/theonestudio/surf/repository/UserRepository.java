package vn.theonestudio.surf.repository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import vn.theonestudio.surf.api.ApiCallException;
import vn.theonestudio.surf.config.AuthorizationConfiguration;
import vn.theonestudio.surf.datasource.UserDataSource;
import vn.theonestudio.surf.dto.request.AuthenticationRequest;
import vn.theonestudio.surf.dto.request.CreateUserRequest;
import vn.theonestudio.surf.dto.response.AuthenticationResponse;
import vn.theonestudio.surf.dto.response.UserResponse;
import vn.theonestudio.surf.model.UserModel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserRepository {

    public static final int ACCESS_TOKEN_EXPIRES_IN_MINUTES = 50000; // For UAT Testing

    AuthorizationConfiguration authorizationConfiguration;

    UserDataSource userDataSource;

    // Utility methods
    public UserResponse parseResponse(UUID userId) {
        Optional<UserModel> userModel = userDataSource.findById(userId);
        return parseResponse(userModel.orElse(null));
    }

    public UserResponse parseResponse(UserModel userModel) {
        if(userModel == null) return null;
        return UserResponse.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .employeeId(userModel.getEmployeeId())
                .build();
    }

    public String grantAccessToken(String userId, String role) throws ApiCallException {
        try {
            long iat = System.currentTimeMillis() / 1000;
            long exp = iat + Duration.ofMinutes(ACCESS_TOKEN_EXPIRES_IN_MINUTES).toSeconds();

            JwtEncoderParameters parameters = JwtEncoderParameters
                    .from(JwsHeader.with(SignatureAlgorithm.RS256).build(),
                            JwtClaimsSet
                                    .builder()
                                    .subject(userId)
                                    .claim("role", role)
                                    .issuedAt(Instant.ofEpochSecond(iat))
                                    .expiresAt(Instant.ofEpochSecond(exp))
                                    .build());


            return authorizationConfiguration.jwtEncoder().encode(parameters).getTokenValue();
        } catch (Exception e) {
            throw new ApiCallException("Failed to grant access token. Detail: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Action methods
    public AuthenticationResponse auth(AuthenticationRequest request) {
        Optional<UserModel> userOpt = userDataSource.findByEmployeeIdAndPassword(request.getEmployeeId(), request.getPassword());
        return userOpt.map(userModel -> {
            try {
                return AuthenticationResponse.builder()
                        .id(userModel.getId())
                        .name(userModel.getName())
                        .employeeId(userModel.getEmployeeId())
                        .accessToken(grantAccessToken(userModel.getId().toString(), userModel.getRole()))
                        .build();
            } catch (ApiCallException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

    public UserResponse createUser(CreateUserRequest request) {
        UserModel userModel = UserModel.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .employeeId(request.getEmployeeId())
                .role(request.getRole())
                .password("123456")
                .teamId(request.getTeamId())
                .build();
        userDataSource.save(userModel);

        return parseResponse(userModel);
    }

    public List<UserResponse> getAllUsersInTeam(UUID teamId) {
        List<UserModel> users = userDataSource.findAllByTeamId(teamId);
        return users.stream().map(this::parseResponse).toList();
    }
}
