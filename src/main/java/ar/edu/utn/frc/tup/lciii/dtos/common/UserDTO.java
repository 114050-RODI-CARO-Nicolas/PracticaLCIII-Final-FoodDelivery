package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.model.UserLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class UserDTO {

    private Long id;

    private String name;

    @JsonProperty("last_name")
    private String lastName;

    private String nickname;

    private String neighborhood;

    private UserLevel level;
}
