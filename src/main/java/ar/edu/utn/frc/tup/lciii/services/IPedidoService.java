package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.RequestOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestProductOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseProductOrderDTO;

public interface IPedidoService {

    ResponseOrderDTO crearPedido(RequestOrderDTO requestDTO);

}
