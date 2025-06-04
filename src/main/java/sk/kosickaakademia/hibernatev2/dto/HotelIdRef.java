package sk.kosickaakademia.hibernatev2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Just a Hotel identifier (use only hotel.id when creating/updating a Room)")
public class HotelIdRef {

    @Schema(
            description = "Primary key of an existing Hotel",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1"
    )
    public Integer id;
}
