package sk.kosickaakademia.hibernatev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kosickaakademia.hibernatev2.entity.Room;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    // example, custom finder:
    List<Room> findByHotelId(Integer hotelId);
}
