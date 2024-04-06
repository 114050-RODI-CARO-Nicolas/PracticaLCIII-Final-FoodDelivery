package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Menu;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ProductDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.model.Category;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.IRestauranteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestauranteServiceImp implements IRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    BarrioRepository barrioRepository;

    ObjectMapper objectMapper = new ObjectMapper();


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

    @Override
    public List<ProductDTO> obtenerMenusPorRestaurante(long idRestaurante)
    {
        List<ProductDTO> productDTOList = new ArrayList<>();

        Restaurante foundRestaurant = restauranteRepository.findById(idRestaurante).orElseThrow(
                ()-> new EntityNotFoundException("No se encontro un restaurante con ese ID")
        );

        List<Menu> restaurantMenuList = foundRestaurant.getMenus();

        for (Menu menu : restaurantMenuList
             ) {

            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(menu.getNombre());
            productDTO.setPrice(menu.getPrecio());
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setName(menu.getRestaurante().getNombre());
            productDTO.setLocation(restaurantDTO);
            productDTO.setPreparationTime(menu.getMinutosPreparacion());
            Category categoryModel = new Category();
            categoryModel.setName(menu.getCategoria().getNombre());
            productDTO.setCategory(categoryModel);
            productDTOList.add(productDTO);


        }
    return productDTOList;
    }


}
