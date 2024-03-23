package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.domain.Barrio;
import ar.edu.utn.frc.tup.lciii.domain.Local;
import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ProductDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.services.implementations.BarrioServiceImp;
import ar.edu.utn.frc.tup.lciii.services.implementations.LocalServiceImp;
import ar.edu.utn.frc.tup.lciii.services.implementations.MenuServiceImp;
import ar.edu.utn.frc.tup.lciii.services.implementations.RestauranteServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteControler {

    @Autowired
    RestauranteServiceImp restauranteServiceImp;

    @Autowired
    LocalServiceImp localServiceImp;

    @Autowired
    MenuServiceImp menuServiceImp;

    @Autowired
    BarrioServiceImp barrioServiceImp;



    @PostMapping
    public ResponseEntity<HashMap<String, Boolean>> altaRestaurante(@Valid @RequestBody RestaurantDTO requestDTO)
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

    @PostMapping("/{idRestaurante}/locales/{idLocal}/menus")
    public ResponseEntity<HashMap<String, Boolean>> altaMenu(@Valid @PathVariable long idRestaurante, @RequestBody ProductDTO requestDTO)
    {
        boolean createdSuccesfully= menuServiceImp.altaMenu(idRestaurante, requestDTO);
        HashMap<String, Boolean> hashMapResult = new HashMap<>();
        hashMapResult.put("Creado:", createdSuccesfully);
        return new ResponseEntity<>(hashMapResult, HttpStatus.CREATED);

    }

    @GetMapping("/{idRestaurante}/locales")
    public ResponseEntity<List<Local>> obtenerLocalesPorRestaurant(@PathVariable long idRestaurante)
    {
        List<Local> lstLocales = localServiceImp.obtenerLocalesPorRestaurant(idRestaurante);
        return new ResponseEntity<>(lstLocales, HttpStatus.OK);
    }


    @GetMapping("/barrios/{idBarrio}/locales")
    public ResponseEntity<List<Local>> obtenerLocalesPorBarrio(@PathVariable long idBarrio){

       List<Local> lstLocales = barrioServiceImp.obtenerLocalesDelBarrio(idBarrio);

       return new ResponseEntity<>(lstLocales, HttpStatus.OK);
    }








}
