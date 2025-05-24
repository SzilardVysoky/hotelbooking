package sk.kosickaakademia.hibernatev2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.entity.Hotel;
import sk.kosickaakademia.hibernatev2.service.HotelService;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable Integer id) {
        return hotelService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Hotel> create(@RequestBody Hotel hotel) {
        Hotel saved = hotelService.create(hotel);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> update(
            @PathVariable Integer id,
            @RequestBody Hotel hotelDetails
    ) {
        return ResponseEntity.ok(hotelService.update(id, hotelDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
