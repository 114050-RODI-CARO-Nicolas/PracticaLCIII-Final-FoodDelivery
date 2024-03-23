package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;

import java.util.List;

public interface ILocalService {

    boolean altaLocal(Long idRestaurante, LocationDTO requestDTO);

    List<Local> obtenerLocalesPorRestaurant(long idRestaurante);
}
