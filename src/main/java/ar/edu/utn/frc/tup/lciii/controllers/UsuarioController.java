package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.UserDTO;
import ar.edu.utn.frc.tup.lciii.services.implementations.UsuarioServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioServiceImp usuarioServiceImp;

    @PostMapping()
    public ResponseEntity<HashMap<String, Boolean>> altaUsuario(UserDTO requestDTO)
    {
        boolean createdSuccesfully= usuarioServiceImp.altaUsuario(requestDTO);
        HashMap<String, Boolean> hashMapResult = new HashMap<>();
        hashMapResult.put("Creado:", createdSuccesfully);
        return new ResponseEntity<>(hashMapResult, HttpStatus.CREATED);
    }


}
