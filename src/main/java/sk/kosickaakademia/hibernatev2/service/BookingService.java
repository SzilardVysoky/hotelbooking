package sk.kosickaakademia.hibernatev2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.kosickaakademia.hibernatev2.entity.RoomBooking;
import sk.kosickaakademia.hibernatev2.repository.RoomBookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final RoomBookingRepository bookingRepo;

    public BookingService(RoomBookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Transactional
    public RoomBooking createBooking(RoomBooking booking) {

        List<RoomBooking> existing = bookingRepo.findByRoomId(booking.getRoom().getId());

        LocalDate start = booking.getBookingDate();
        LocalDate end   = booking.getEndDate();

        for (RoomBooking b : existing) {
            // If same record (same ID), skip it
            if (b.getId() != null && b.getId().equals(booking.getId())) {
                continue;
            }

            if (start.isBefore(b.getEndDate()) && end.isAfter(b.getBookingDate())) {
                throw new IllegalStateException(
                        "Room " + booking.getRoom().getId() +
                                " is already booked from " +
                                b.getBookingDate() + " to " + b.getEndDate()
                );
            }
        }

        return bookingRepo.save(booking);
    }

    public List<RoomBooking> findByCustomer(Integer customerId) {
        return bookingRepo.findByCustomerId(customerId);
    }

    public List<RoomBooking> findByRoom(Integer roomId) {
        return bookingRepo.findByRoomId(roomId);
    }

    public Optional<RoomBooking> findById(Integer id) { return bookingRepo.findById(id); }
}
