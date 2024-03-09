package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO implements Serializable {

    private Long id;

    private String name;

    private String nickname;

    private String email;


    //@JsonIgnore
    private String password;

}
