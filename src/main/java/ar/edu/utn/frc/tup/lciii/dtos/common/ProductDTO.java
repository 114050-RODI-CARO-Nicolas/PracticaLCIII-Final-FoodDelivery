package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private RestaurantDTO location;

    private BigDecimal price;

    @JsonProperty("preparation_time")
    private Integer preparationTime;

    private Category category;
}
