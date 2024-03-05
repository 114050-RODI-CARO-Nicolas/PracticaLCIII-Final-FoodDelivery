package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.model.RushHour;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Long id;

    private String name;

    private String neighborhood;

    @JsonProperty("rush_hours")
    private List<RushHour> rushHours;

    @JsonProperty("max_capacity")
    private Integer maxCapacity;
}
