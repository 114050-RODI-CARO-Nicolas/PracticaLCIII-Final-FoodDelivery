package ar.edu.utn.frc.tup.lciii.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Restaurante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotEmpty
    @Column(unique = true)
    private String nombre;

    @NotBlank
    @NotEmpty
    private String nickname;

    @NotBlank
    @NotEmpty
    @Email
    private String email;



    @NotBlank(message = "Password should not be a blank")
    @NotEmpty(message = "Password should not be an empty string")
    @Size(min = 8, max = 15,  message = "Password must have between 8 and 15 characters")
    @Pattern(regexp = "(?=.*\\\\d)", message = "Password must contain at least one digit")
    private String contraseña;

    @OneToMany(mappedBy="restaurante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Local> locales= new ArrayList<>();

    @OneToMany(mappedBy="restaurante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Menu> menus = new ArrayList<>();








}
