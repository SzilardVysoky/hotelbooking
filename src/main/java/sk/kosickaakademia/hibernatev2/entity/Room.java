package sk.kosickaakademia.hibernatev2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.kosickaakademia.hibernatev2.dto.HotelIdRef;

@Entity
@Table(name = "Room")
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(
            description = "Room number",
            example     = "601",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String number;

    @Column(name = "room_type", nullable = false)
    @Schema(
            description = "Type of room",
            example     = "Single",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String roomType;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnoreProperties(value = { "rooms" }, allowSetters = true)
    @Schema(
            description = "Reference to an existing Hotel by its id",
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = HotelIdRef.class
    )
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<RoomBooking> bookings;
}
