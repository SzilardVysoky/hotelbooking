package sk.kosickaakademia.hibernatev2.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import sk.kosickaakademia.hibernatev2.entity.Hotel;
import sk.kosickaakademia.hibernatev2.entity.Room;
import sk.kosickaakademia.hibernatev2.repository.HotelRepository;
import sk.kosickaakademia.hibernatev2.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepo;
    private final HotelRepository hotelRepo;

    public RoomService(RoomRepository roomRepo, HotelRepository hotelRepo) {
        this.roomRepo = roomRepo;
        this.hotelRepo = hotelRepo;
    }

    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    public List<Room> findByHotel(Integer hotelId) {
        return roomRepo.findByHotelId(hotelId);
    }

    public Optional<Room> findById(Integer id) {
        return roomRepo.findById(id);
    }

    public Room create(Room room) {
        // ensure hotel exists
        Hotel hotel = hotelRepo.findById(room.getHotel().getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found: " + room.getHotel().getId()));
        room.setHotel(hotel);
        return roomRepo.save(room);
    }

    public Room update(Integer id, Room roomDetails) {
        Room room = roomRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + id));
        room.setNumber(roomDetails.getNumber());
        room.setRoomType(roomDetails.getRoomType());
        // optionally update hotel
        if (roomDetails.getHotel() != null) {
            Hotel hotel = hotelRepo.findById(roomDetails.getHotel().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Hotel not found: " + roomDetails.getHotel().getId()));
            room.setHotel(hotel);
        }
        return roomRepo.save(room);
    }

    public void delete(Integer id) {
        roomRepo.deleteById(id);
    }
}
