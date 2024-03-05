package ar.edu.utn.frc.tup.lciii.model;

import lombok.*;

import java.time.DayOfWeek;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RushHour {

    private DayOfWeek day;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
}
