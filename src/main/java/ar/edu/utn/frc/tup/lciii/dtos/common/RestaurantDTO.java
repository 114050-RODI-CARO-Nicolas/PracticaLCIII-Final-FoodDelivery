package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

@Builder
public class RestaurantDTO {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    @JsonIgnore
    private String password;

}
