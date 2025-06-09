package vn.theonestudio.surf.datasource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.theonestudio.surf.model.UserModel;

import java.util.UUID;

@Repository
public interface UserDataSource extends MongoRepository<UserModel, UUID> {
}
