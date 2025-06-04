package sk.kosickaakademia.hibernatev2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Hotel")
@Getter
@Setter
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(
            description = "Hotel name",
            accessMode  = Schema.AccessMode.READ_WRITE,
            example     = "Grand Plaza"
    )
    private String name;

    @Column(nullable = false)
    @Schema(
            description = "Hotel location",
            accessMode  = Schema.AccessMode.READ_WRITE,
            example     = "New York"
    )
    private String location;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Schema(hidden = true)
    private List<Room> rooms;
}
