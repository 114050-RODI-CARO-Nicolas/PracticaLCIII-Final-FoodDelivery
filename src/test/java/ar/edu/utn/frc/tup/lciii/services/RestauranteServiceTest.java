package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.common.LocationDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RestaurantDTO;
import ar.edu.utn.frc.tup.lciii.repositories.BarrioRepository;
import ar.edu.utn.frc.tup.lciii.repositories.RestauranteRepository;
import ar.edu.utn.frc.tup.lciii.services.implementations.LocalServiceImp;
import ar.edu.utn.frc.tup.lciii.services.implementations.RestauranteServiceImp;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RestauranteServiceTest {

    @Mock
    RestauranteRepository restauranteRepository;

    @Mock
    BarrioRepository barrioRepository;



    @InjectMocks
    RestauranteServiceImp restauranteServiceImp;

    @InjectMocks
    LocalServiceImp localServiceImp;

    @Test
    public void chequearSiSeCreaNuevoRestaurante()
    {
        RestaurantDTO mockRestauranteDTO = new RestaurantDTO();

        mockRestauranteDTO.setName("Restaurante del Test");
        mockRestauranteDTO.setNickname("TestResto");
        mockRestauranteDTO.setEmail("tstresto@gmail.com");
        mockRestauranteDTO.setPassword("123456");

        assertNotNull(mockRestauranteDTO);
         boolean mockResult=true;
        assertEquals(mockResult, restauranteServiceImp.altaRestaurante(mockRestauranteDTO) );

    }

    @Test
    @Disabled
    public void chequearSiRestauranteNoValidoTiraExcepcion()
    {

        LocationDTO mockLocationDTO=new LocationDTO();
        long mockInvalidRestaurantId=5;

        assertThrows(EntityNotFoundException.class, ()-> localServiceImp.altaLocal(mockInvalidRestaurantId, mockLocationDTO));










    }





}
