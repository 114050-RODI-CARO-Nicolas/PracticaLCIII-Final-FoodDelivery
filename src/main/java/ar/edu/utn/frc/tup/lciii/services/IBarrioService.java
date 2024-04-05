package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;

import java.util.List;

public interface IBarrioService {

    List<RestaurantDTO> obtenerRestaurantesDelBarrio(String barrio);

}
