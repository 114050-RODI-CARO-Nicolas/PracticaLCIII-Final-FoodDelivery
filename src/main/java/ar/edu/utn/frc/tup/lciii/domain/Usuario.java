package ar.edu.utn.frc.tup.lciii.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String nickname;


    @ManyToOne
    @JoinColumn(name = "id_barrio", referencedColumnName = "id")
    private Barrio barrio;


    @Min(value=1, message = "Value must be greater than 0")
    @Max(value=5, message = "Value must be lesser than 5")
    private Integer nivel;


}
