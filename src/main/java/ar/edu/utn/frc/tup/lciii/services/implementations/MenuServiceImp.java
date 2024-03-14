package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.CategoriaMenu;
import ar.edu.utn.frc.tup.lciii.domain.Menu;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.ProductDTO;
import ar.edu.utn.frc.tup.lciii.repositories.CategoriaRepository;
import ar.edu.utn.frc.tup.lciii.repositories.MenuRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.IMenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MenuServiceImp implements IMenuService {


    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    RestauranteRepository restauranteRepository;


    @Override
    public boolean altaMenu(long restauranteId, ProductDTO requestDTO) {

        boolean createdSuccesfully = false;
        try {

            Restaurante foundRestaurant = restauranteRepository.findById(restauranteId).orElseThrow( ()-> new EntityNotFoundException("Source: MenuService.altaMenu() - No se encontraron menus con el ID solicitado'"));
            CategoriaMenu foundCategory = categoriaRepository.findByNombre(requestDTO.getCategory().getName());
            Menu newMenu = new Menu();
            newMenu.setRestaurante(foundRestaurant);
            newMenu.setCategoria(foundCategory);
            newMenu.setNombre(requestDTO.getName());
            newMenu.setPrecio(requestDTO.getPrice());
            newMenu.setMinutosPreparacion(requestDTO.getPreparationTime());

            restauranteRepository.save(foundRestaurant);
            {
                createdSuccesfully=true;
            }

        }
        catch (EntityNotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw ex;
        }

        return createdSuccesfully;

    }
}
