package ar.edu.utn.frc.tup.lciii.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.DayOfWeek;




//@Entity
@Setter
public class DEPRECATED_HorarioAtencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private DayOfWeek _day;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;

    @ManyToOne
    @JoinColumn(name="id_local", referencedColumnName = "id")
    private Local local;
}


