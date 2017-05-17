package ua.iasa.repository;

import org.springframework.data.repository.CrudRepository;
import ua.iasa.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Room findByRoomType(String roomType);
}
