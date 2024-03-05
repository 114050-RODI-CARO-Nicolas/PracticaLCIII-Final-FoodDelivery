package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.services.implementations.LocalServiceImp;
import ar.edu.utn.frc.tup.lciii.services.implementations.RestauranteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteControler {

    @Autowired
    RestauranteServiceImp restauranteServiceImp;

    @Autowired
    LocalServiceImp localServiceImp;

    @PostMapping
    public ResponseEntity<HashMap<String, Boolean>> altaRestaurante(@RequestBody RestaurantDTO requestDTO)
    {
       boolean createdSuccesfully= restauranteServiceImp.altaRestaurante(requestDTO);
       HashMap<String, Boolean> hashMapResult = new HashMap<>();
       hashMapResult.put("Creado:", createdSuccesfully);
       return new ResponseEntity<>(hashMapResult, HttpStatus.CREATED);
    };

    @PostMapping("/{idRestaurante}/locales")
    public ResponseEntity<HashMap<String, Boolean>> altaLocal(@PathVariable Long idRestaurante, @RequestBody  LocationDTO requestDTO)
    {
        boolean createdSuccesfully= localServiceImp.altaLocal(idRestaurante, requestDTO);
        HashMap<String, Boolean> hashMapResult = new HashMap<>();
        hashMapResult.put("Creado:", createdSuccesfully);
        return new ResponseEntity<>(hashMapResult, HttpStatus.CREATED);

    }




}
