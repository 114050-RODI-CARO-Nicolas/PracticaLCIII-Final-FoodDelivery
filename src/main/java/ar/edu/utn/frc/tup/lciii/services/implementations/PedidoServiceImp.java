package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.domain.Restaurante;
import ar.edu.utn.frc.tup.lciii.domain.Usuario;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestProductOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseOrderDTO;
import ar.edu.utn.frc.tup.lciii.repositories.LocalRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.repositories.UsuarioRepository;
import ar.edu.utn.frc.tup.lciii.services.IPedidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImp implements IPedidoService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    LocalRepository localRepository;


    @Override
    public ResponseOrderDTO crearPedido(RequestOrderDTO requestDTO) {

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();


        try {

            Usuario foundUser = usuarioRepository.findById(requestDTO.getUserId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro usuario con el ID enviado"));
            Restaurante foundRestaurant = restauranteRepository.findById(requestDTO.getRestaurantId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro restaurant con el ID enviado"));
            Local foundLocation = localRepository.findById(requestDTO.getLocationId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro local con l ID enviado") );



        }

        catch (Exception ex)

        {
            throw ex;

        }




        return responseOrderDTO;




    }
}
