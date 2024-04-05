package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.LocalRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.IBarrioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class BarrioServiceImp implements IBarrioService  {


    @Autowired
    BarrioRepository barrioRepository;

    @Autowired
    LocalRepository localRepository;

    @Autowired
    RestauranteRepository restauranteRepository;


    @Override
    public List<RestaurantDTO> obtenerRestaurantesDelBarrio(String barrio)
    {

        List<RestaurantDTO> restaurantDTOList = new ArrayList<>();


        Barrio barrioEncontrado = barrioRepository.findByNombre(barrio);
        if(barrioEncontrado==null){
            throw new EntityNotFoundException("Barrio not found with that id");
        }

        List <Local> locals = barrioEncontrado.getLocalesDelBarrio();


        HashSet<Restaurante> restaurantesAgregados = new HashSet<>();

        for (Local local : locals)
        {
            if(!restaurantesAgregados.contains(local.getRestaurante()))
            {
                RestaurantDTO restaurantDTO = new RestaurantDTO();
                restaurantDTO.setName(local.getRestaurante().getNombre());
                restaurantDTO.setEmail(local.getRestaurante().getEmail());
                restaurantesAgregados.add(local.getRestaurante());
                restaurantDTOList.add(restaurantDTO);
            }
        }

        return restaurantDTOList;
    }
}
