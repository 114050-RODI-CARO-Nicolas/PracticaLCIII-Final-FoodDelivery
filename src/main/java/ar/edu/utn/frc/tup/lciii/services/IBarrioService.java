package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;

import java.util.List;

public interface IBarrioService {

    List<Local> obtenerLocalesDelBarrio(long idBarrio);

}
