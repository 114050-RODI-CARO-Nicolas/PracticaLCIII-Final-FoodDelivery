package ar.edu.utn.frc.tup.lciii.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Double precio;
    private Integer minutosPreparacion;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private Restaurante restaurante;


}
