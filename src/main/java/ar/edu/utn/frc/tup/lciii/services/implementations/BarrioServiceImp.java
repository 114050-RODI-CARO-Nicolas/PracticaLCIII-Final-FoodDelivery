package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.LocalRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.IBarrioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Local> obtenerLocalesDelBarrio(long idBarrio)
    {

        Barrio barrioEncontrado = barrioRepository.findById(idBarrio).orElseThrow( ()-> new EntityNotFoundException("Barrio not found with that id"));

        return barrioEncontrado.getLocalesDelBarrio();

    }
}
