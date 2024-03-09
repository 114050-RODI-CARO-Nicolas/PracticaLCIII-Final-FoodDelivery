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
    private String nombre;

    @NotBlank
    @NotEmpty
    private String nickname;

    @NotBlank
    @NotEmpty
    @Email
    private String email;



    @NotBlank
    @NotEmpty
    @Size(min = 8, max = 15)
    @Pattern(regexp = "(?=.*\\\\d)", message = "Password must contain at least one digit")
    private String contraseña;

    @OneToMany(mappedBy="restaurante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Local> locales= new ArrayList<>();
    @OneToMany
    List<Menu> menus = new ArrayList<>();








}
