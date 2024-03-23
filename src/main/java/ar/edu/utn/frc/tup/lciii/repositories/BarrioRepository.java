package ar.edu.utn.frc.tup.lciii.repositories;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarrioRepository extends JpaRepository<Barrio, Long> {

    Barrio findByNombre(String nombre);
}
