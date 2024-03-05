package ar.edu.utn.frc.tup.lciii.model;

import lombok.Builder;

import java.time.DayOfWeek;

@Builder
public class RushHour {

    private DayOfWeek day;
    private Integer startHour;
    private Integer startMinute;
    private Integer endHour;
    private Integer endMinute;
}
