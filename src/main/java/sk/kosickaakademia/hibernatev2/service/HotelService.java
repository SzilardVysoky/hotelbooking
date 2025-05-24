package sk.kosickaakademia.hibernatev2.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sk.kosickaakademia.hibernatev2.entity.Hotel;
import sk.kosickaakademia.hibernatev2.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private final HotelRepository hotelRepo;

    public HotelService(HotelRepository hotelRepo) {
        this.hotelRepo = hotelRepo;
    }

    public List<Hotel> findAll() {
        return hotelRepo.findAll();
    }

    public Optional<Hotel> findById(Integer id) {
        return hotelRepo.findById(id);
    }

    public Hotel create(Hotel hotel) {
        return hotelRepo.save(hotel);
    }

    public Hotel update(Integer id, Hotel hotelDetails) {
        Hotel hotel = hotelRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found: " + id));
        hotel.setName(hotelDetails.getName());
        hotel.setLocation(hotelDetails.getLocation());
        return hotelRepo.save(hotel);
    }

    public void delete(Integer id) {
        hotelRepo.deleteById(id);
    }
}
