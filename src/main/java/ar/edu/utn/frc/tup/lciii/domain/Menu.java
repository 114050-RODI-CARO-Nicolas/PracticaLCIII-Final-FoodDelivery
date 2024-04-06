package ar.edu.utn.frc.tup.lciii.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @DecimalMin(value="0.0", inclusive = false, message = "Value must be greater than 0")
    private BigDecimal precio;

    @Min(value=1, message = "Value must be greater than 0")
    private Integer minutosPreparacion;


    @ManyToOne
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name="id_categoria", referencedColumnName = "id")
    private CategoriaMenu categoria;




}
