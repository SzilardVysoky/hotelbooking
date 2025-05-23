package sk.kosickaakademia.hibernatev2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.kosickaakademia.hibernatev2.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
