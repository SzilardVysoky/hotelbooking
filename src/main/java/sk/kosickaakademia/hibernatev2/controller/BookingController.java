package sk.kosickaakademia.hibernatev2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.entity.RoomBooking;
import sk.kosickaakademia.hibernatev2.service.BookingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<RoomBooking> getAll(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer roomId
    ) {
        if (customerId != null) {
            return bookingService.findByCustomer(customerId);
        }
        if (roomId != null) {
            return bookingService.findByRoom(roomId);
        }
        // If neither provided, return all (optional: implement findAll in service)
        return bookingService.findByCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoomBooking booking) {
        try {
            RoomBooking saved = bookingService.createBooking(booking);
            return ResponseEntity.status(201).body(saved);
        } catch (IllegalStateException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDates(
            @PathVariable Integer id,
            @RequestBody RoomBooking details
    ) {
        // For simplicity: load existing, set new dates, then call createBooking logic
        details.setId(id);
        try {
            RoomBooking updated = bookingService.createBooking(details);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
