package ar.edu.utn.frc.tup.lciii.dtos.common;

import ar.edu.utn.frc.tup.lciii.model.UserLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDTO implements Serializable  {

    private Long id;

    private String name;


    private String lastName;

    private String nickname;

    private String neighborhood;

    private UserLevel level;



}

