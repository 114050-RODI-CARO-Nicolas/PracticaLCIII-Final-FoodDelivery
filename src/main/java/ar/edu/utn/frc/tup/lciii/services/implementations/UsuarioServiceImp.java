package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Usuario;
import ar.edu.utn.frc.tup.lciii.dtos.common.UserDTO;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.UsuarioRepository;
import ar.edu.utn.frc.tup.lciii.services.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    BarrioRepository barrioRepository;




    @Override
    public boolean altaUsuario(UserDTO requestDTO) {
        boolean createdSuccesfully = false;
        try {
            Usuario nuevoUsuario = new Usuario();
            Barrio barrioEncontrado = barrioRepository.findByNombre(requestDTO.getNeighborhood());
            nuevoUsuario.setBarrio(barrioEncontrado);
            nuevoUsuario.setNombre(requestDTO.getName());
            nuevoUsuario.setApellido(requestDTO.getLastName());
            nuevoUsuario.setNickname(requestDTO.getNickname());

            switch (requestDTO.getLevel())
            {
                case LEVEL_1 -> nuevoUsuario.setNivel(1);
                case LEVEL_2 -> nuevoUsuario.setNivel(2);
                case LEVEL_3 -> nuevoUsuario.setNivel(3);
                case LEVEL_4 -> nuevoUsuario.setNivel(4);
                case LEVEL_5 -> nuevoUsuario.setNivel(5);
            }
            usuarioRepository.save(nuevoUsuario);
            {
                createdSuccesfully=true;
            };


        } catch (Exception ex) {
            throw ex;
        }
        return createdSuccesfully;
    }
}
