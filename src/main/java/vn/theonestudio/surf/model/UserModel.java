package vn.theonestudio.surf.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("users")
public class UserModel {

    @Id
    UUID id;

    String name;

    UUID teamId;

    String employeeId;

    String password;

}
