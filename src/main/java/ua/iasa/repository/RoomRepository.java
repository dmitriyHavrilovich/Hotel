package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Room;

/**
 * Created by Mahaon on 02.05.2017.
 */
public interface RoomRepository extends CrudRepository<Room, Long> {
}
