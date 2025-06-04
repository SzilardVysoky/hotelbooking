package sk.kosickaakademia.hibernatev2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookingUpdateDTO {

    @Schema(
            description  = "ID of the Room being booked",
            example      = "3",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Integer roomId;

    @Schema(
            description  = "ID of the Customer who made this booking",
            example      = "7",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private Integer customerId;

    @Schema(
            description  = "Start date of the booking",
            example      = "2025-06-04",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private LocalDate bookingDate;

    @Schema(
            description  = "End date of the booking",
            example      = "2025-06-08",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    private LocalDate endDate;

    // getters & setters

}
