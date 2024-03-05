package ar.edu.utn.frc.tup.lciii.domain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Barrio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany
    private List<Local> localesDelBarrio = new ArrayList<>();

    @OneToMany
    private List<User> usuariosDelBarrio = new ArrayList<>();




}
