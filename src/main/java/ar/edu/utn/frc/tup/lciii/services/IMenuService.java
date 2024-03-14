package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.ProductDTO;

public interface IMenuService {

    boolean altaMenu(long restauranteId, ProductDTO requestDTO);
}
