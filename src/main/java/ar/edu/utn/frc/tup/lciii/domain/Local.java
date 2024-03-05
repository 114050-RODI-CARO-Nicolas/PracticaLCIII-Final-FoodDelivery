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


    @ManyToOne
    @JoinColumn(name = "id_barrio", referencedColumnName = "id")
    private Barrio barrio;
    private Integer numeroPedidosMismoTiempo;

    @OneToMany(mappedBy = "local", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<HorarioAtencion> rushHours = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private Restaurante restaurante;




}
