package ar.edu.utn.frc.tup.lciii.domain;


import jakarta.persistence.*;
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
    private String nombre;

    private String nickname;
    private String email;
    private String contraseña;

    @OneToMany(mappedBy="restaurante", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Local> locales= new ArrayList<>();
    @OneToMany
    List<Menu> menus = new ArrayList<>();








}
