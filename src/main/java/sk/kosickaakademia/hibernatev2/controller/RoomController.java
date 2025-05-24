package sk.kosickaakademia.hibernatev2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.entity.Room;
import sk.kosickaakademia.hibernatev2.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAll(@RequestParam(required = false) Integer hotelId) {
        if (hotelId != null) {
            return roomService.findByHotel(hotelId);
        }
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Integer id) {
        return roomService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        Room saved = roomService.create(room);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> update(
            @PathVariable Integer id,
            @RequestBody Room details
    ) {
        return ResponseEntity.ok(roomService.update(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
