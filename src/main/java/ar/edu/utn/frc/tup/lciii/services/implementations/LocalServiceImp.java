package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.DEPRECATED_HorarioAtencion;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.model.RushHour;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.LocalRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.ILocalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;

@Service

public class LocalServiceImp implements ILocalService {

    @Autowired
    LocalRepository localRepository;

    @Autowired
    BarrioRepository barrioRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean altaLocal(Long restaurantId, LocationDTO requestDTO) {

        boolean createdSuccesfully=false;


        try {
            Restaurante restauranteEncontrado = restauranteRepository.findById(restaurantId).orElseThrow( ()-> new EntityNotFoundException("Restaurante buscado no existe"));
            Local nuevoLocal = new Local();
            Barrio barrioDelNuevoLocal = barrioRepository.findByNombre(requestDTO.getNeighborhood());
            nuevoLocal.setRestaurante(restauranteEncontrado);
            nuevoLocal.setBarrio(barrioDelNuevoLocal);
          //  barrioDelNuevoLocal.agregarLocal(nuevoLocal);

            nuevoLocal.setNombre(requestDTO.getName());

            if(requestDTO.getRushHours().size()==0) {
                throw new RuntimeException("Source: LocalServiceImp-altaLocal()- El Local no tiene horarios de atencion validos");
            }



            List<RushHour> rushHoursFromRequestDTO = requestDTO.getRushHours();
            String operationHoursJsonData = objectMapper.writeValueAsString(rushHoursFromRequestDTO);



            nuevoLocal.setNumeroPedidosMismoTiempo(requestDTO.getMaxCapacity());
            nuevoLocal.setOperationHoursJsonData(operationHoursJsonData);

           // restauranteEncontrado.getLocales().add(nuevoLocal);

            localRepository.save(nuevoLocal);
            {
                createdSuccesfully=true;
            };

        }

        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        catch (RuntimeException ex){
            throw ex;
        }
        catch (Exception ex)
        {
            throw ex;
        }



        return createdSuccesfully;


    }


    @Override
    public List<Local> obtenerLocalesPorRestaurant(long idRestaurante)
    {

        Restaurante foundRestaurant = restauranteRepository.findById(idRestaurante).orElseThrow( ()-> new EntityNotFoundException("No se encontro el restaurante con ese ID"));
        return foundRestaurant.getLocales();

    }



}
