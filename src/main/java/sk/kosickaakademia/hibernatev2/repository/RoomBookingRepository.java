package sk.kosickaakademia.hibernatev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kosickaakademia.hibernatev2.entity.RoomBooking;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer> {
    List<RoomBooking> findByCustomerId(Integer customerId);
    List<RoomBooking> findByRoomId(Integer roomId);
}
