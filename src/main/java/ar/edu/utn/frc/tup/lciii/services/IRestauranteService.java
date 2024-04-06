package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ProductDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;

import java.util.List;

public interface IRestauranteService {

    boolean altaRestaurante(RestaurantDTO restaurantDTO);

    List<ProductDTO> obtenerMenusPorRestaurante(long idRestaurante);




}
