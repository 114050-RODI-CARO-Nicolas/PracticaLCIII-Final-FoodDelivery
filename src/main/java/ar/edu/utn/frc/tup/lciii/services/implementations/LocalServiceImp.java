package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.HorarioAtencion;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.model.RushHour;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.LocalRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.ILocalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean altaLocal(Long restaurantId, LocationDTO requestDTO) {

        boolean createdSuccesfully=false;


        try {
            Restaurante restauranteEncontrado = restauranteRepository.findById(restaurantId).orElseThrow( ()-> new EntityNotFoundException("Restaurante buscado no existe"));
            Local nuevoLocal = new Local();
            Barrio barrioDelNuevoLocal = barrioRepository.findByNombre(requestDTO.getNeighborhood());
            nuevoLocal.setBarrio(barrioDelNuevoLocal);
            nuevoLocal.setNombre(requestDTO.getName());

            if(requestDTO.getRushHours().size()==0) {
                throw new RuntimeException("Source: LocalServiceImp-altaLocal()- El Local no tiene horarios de atencion validos");
            }

            List<HorarioAtencion> horariosDeAtencionDelNuevoLocal = new ArrayList<>();

            List<RushHour> rushHoursFromRequestDTO = requestDTO.getRushHours();

            for (RushHour rushHour : rushHoursFromRequestDTO
            ) {
                HorarioAtencion horarioAtencion = new HorarioAtencion();
                horarioAtencion.setLocal(nuevoLocal);
                horarioAtencion.setDay(rushHour.getDay());
                horarioAtencion.setStartHour(rushHour.getStartHour());
                horarioAtencion.setStartMinute(rushHour.getStartMinute());
                horarioAtencion.setEndHour(rushHour.getEndHour());
                horarioAtencion.setEndMinute(rushHour.getEndMinute());
                horariosDeAtencionDelNuevoLocal.add(horarioAtencion);

            }
            nuevoLocal.setNumeroPedidosMismoTiempo(requestDTO.getMaxCapacity());
            nuevoLocal.setRushHours(horariosDeAtencionDelNuevoLocal);
            restauranteEncontrado.getLocales().add(nuevoLocal);
            restauranteRepository.save(restauranteEncontrado);

        }

        catch (RuntimeException ex){
            throw ex;
        }
        catch (Exception ex)
        {
            throw ex;
        }


        createdSuccesfully=true;
        return createdSuccesfully;


    }
}
