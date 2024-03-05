package ar.edu.utn.frc.tup.lciii.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nickname;


    @ManyToOne
    @JoinColumn(name = "id_barrio", referencedColumnName = "id")
    private Barrio barrio;


    private Integer nivel;


}
