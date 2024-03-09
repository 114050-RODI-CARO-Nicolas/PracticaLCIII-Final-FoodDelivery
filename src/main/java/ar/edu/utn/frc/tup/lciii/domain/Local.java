package ar.edu.utn.frc.tup.lciii.domain;

import ar.edu.utn.frc.tup.lciii.model.RushHour;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Setter
public class Local implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private Integer numeroPedidosMismoTiempo;

    private String operationHoursJsonData;


    @ManyToOne
    @JoinColumn(name = "id_barrio", referencedColumnName = "id")
    private Barrio barrio;





    @ManyToOne
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private Restaurante restaurante;








}
