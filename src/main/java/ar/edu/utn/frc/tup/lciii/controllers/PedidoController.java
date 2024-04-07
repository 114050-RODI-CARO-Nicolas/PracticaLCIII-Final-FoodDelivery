package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.RequestOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseOrderDTO;
import ar.edu.utn.frc.tup.lciii.services.implementations.PedidoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    PedidoServiceImp pedidoServiceImp;

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> crearPedido(@RequestBody RequestOrderDTO requestDTO)
    {
        ResponseOrderDTO responseDTO = pedidoServiceImp.crearPedido(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

    }


}
