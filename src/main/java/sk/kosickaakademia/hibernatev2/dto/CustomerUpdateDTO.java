package sk.kosickaakademia.hibernatev2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateDTO {

    @Schema(
            description  = "Customer’s full name",
            example      = "Szilard Vysoky",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String name;

    @Schema(
            description  = "Customer’s email address",
            example      = "szilard.vysoky@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String email;
}
