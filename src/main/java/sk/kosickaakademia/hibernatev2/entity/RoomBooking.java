package sk.kosickaakademia.hibernatev2.entity;

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
    @Schema(
            description  = "Reference to the Room being booked (only 'id' is needed here)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonIgnoreProperties(
            value = { "number", "roomType", "hotel", "bookings" },
            allowSetters = true
    )
    private Room room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Schema(
            description  = "Reference to the Customer who made this booking (only 'id' is needed)",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @JsonIgnoreProperties(
            value = { "name", "email", "bookings" },
            allowSetters = true
    )
    private Customer customer;

    @Column(name = "booking_date", nullable = false)
    @Schema(
            description  = "Start date of the booking",
            example      = "2025-06-04",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate bookingDate;

    @Column(name = "end_date", nullable = false)
    @Schema(
            description  = "End date of the booking",
            example      = "2025-06-08",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate endDate;
}
