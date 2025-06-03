package sk.kosickaakademia.hibernatev2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RoomBooking")
@Getter
@Setter
@NoArgsConstructor
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties(value = { "bookings" }, allowSetters = true)
    private Customer customer;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
