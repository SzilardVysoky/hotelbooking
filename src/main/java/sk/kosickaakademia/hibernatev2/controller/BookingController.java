package sk.kosickaakademia.hibernatev2.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.hibernatev2.dto.BookingCreateDTO;
import sk.kosickaakademia.hibernatev2.dto.BookingUpdateDTO;
import sk.kosickaakademia.hibernatev2.entity.Customer;
import sk.kosickaakademia.hibernatev2.entity.Room;
import sk.kosickaakademia.hibernatev2.entity.RoomBooking;
import sk.kosickaakademia.hibernatev2.repository.CustomerRepository;
import sk.kosickaakademia.hibernatev2.repository.RoomRepository;
import sk.kosickaakademia.hibernatev2.service.BookingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final RoomRepository roomRepo;
    private final CustomerRepository customerRepo;

    public BookingController(BookingService bookingService, RoomRepository roomRepo, CustomerRepository customerRepo) {
        this.bookingService = bookingService;
        this.roomRepo = roomRepo;
        this.customerRepo = customerRepo;
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
        // If neither provided, return all
        return bookingService.findByCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingCreateDTO dto) {
        // Room by dto.getRoomId()
        Room room = roomRepo.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + dto.getRoomId()));
        // Customer by dto.getCustomerId()
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + dto.getCustomerId()));

        // New RoomBooking from the DTO
        RoomBooking booking = new RoomBooking();
        booking.setRoom(room);
        booking.setCustomer(customer);
        booking.setBookingDate(dto.getBookingDate());
        booking.setEndDate(dto.getEndDate());

        // Save
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
            @RequestBody BookingUpdateDTO dto
    ) {
        // 1) Load existing booking
        RoomBooking existing = bookingService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + id));

        // 2) Lookup the Room and Customer entities by their IDs
        Room room = roomRepo.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + dto.getRoomId()));
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + dto.getCustomerId()));

        // 3) from the DTO
        existing.setRoom(room);
        existing.setCustomer(customer);
        existing.setBookingDate(dto.getBookingDate());
        existing.setEndDate(dto.getEndDate());

        // 4) Re‐run, check and save
        try {
            RoomBooking updated = bookingService.createBooking(existing);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // check if exists first:
        if (bookingService.findById(id).isEmpty()) {
             throw new EntityNotFoundException("Booking not found: " + id);
        }

        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
