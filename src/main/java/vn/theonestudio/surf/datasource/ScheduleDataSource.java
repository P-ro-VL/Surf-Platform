package vn.theonestudio.surf.datasource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.theonestudio.surf.model.ScheduleModel;

import java.util.UUID;

@Repository
public interface ScheduleDataSource extends MongoRepository<ScheduleModel, UUID> {
}
