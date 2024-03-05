package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.IRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestauranteServiceImp implements IRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Override
    public boolean altaRestaurante(RestaurantDTO restaurantDTO) {

        boolean createdSuccesfully = false;
        try {
            Restaurante nuevoRestaurante = new Restaurante();
            nuevoRestaurante.setEmail(restaurantDTO.getEmail());
            nuevoRestaurante.setNickname(restaurantDTO.getNickname());
            nuevoRestaurante.setNombre(restaurantDTO.getName());
            nuevoRestaurante.setContraseña(restaurantDTO.getPassword());

            restauranteRepository.save(nuevoRestaurante);
            createdSuccesfully=true;
        } catch (Exception ex){
            throw ex;
        }
        return createdSuccesfully;

    }

}
