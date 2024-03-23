package ar.edu.utn.frc.tup.lciii.domain;

import ar.edu.utn.frc.tup.lciii.model.RushHour;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
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
@Getter
public class Local implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private Integer numeroPedidosMismoTiempo;

    private String operationHoursJsonData;


    @JsonIgnore
    @JoinColumn(name = "id_barrio", referencedColumnName = "id")
    @ManyToOne
    private Barrio barrio;




    @JsonIgnore
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    @ManyToOne
    private Restaurante restaurante;








}
