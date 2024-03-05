package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;

public interface ILocalService {

    boolean altaLocal(Long idRestaurante, LocationDTO requestDTO);
}
