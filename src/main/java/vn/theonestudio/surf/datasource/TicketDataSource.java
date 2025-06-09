package vn.theonestudio.surf.datasource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.theonestudio.surf.model.TicketModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketDataSource extends MongoRepository<TicketModel, UUID> {

    Optional<TicketModel> findByTicketId(String ticketId);
}
