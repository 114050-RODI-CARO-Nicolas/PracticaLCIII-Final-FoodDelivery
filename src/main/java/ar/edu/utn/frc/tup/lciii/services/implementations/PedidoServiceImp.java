package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.*;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestProductOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseOrderDTO;
import ar.edu.utn.frc.tup.lciii.repositories.*;
import ar.edu.utn.frc.tup.lciii.services.IPedidoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImp implements IPedidoService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    LocalRepository localRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MenuRepository menuRepository;

    @Override
    public ResponseOrderDTO crearPedido(RequestOrderDTO requestDTO) {

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();


        try {
            Pedido newPedido = new Pedido();

            Usuario foundUser = usuarioRepository.findById(requestDTO.getUserId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro usuario con el ID enviado"));
            Restaurante foundRestaurant = restauranteRepository.findById(requestDTO.getRestaurantId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro restaurant con el ID enviado"));
            Local foundLocation = localRepository.findById(requestDTO.getLocationId()).orElseThrow( ()-> new EntityNotFoundException("No se encontro local con l ID enviado") );


            List<RequestProductOrderDTO> requestProductOrderDTOList = requestDTO.getProducts();
            List<DetallePedido> detallePedidos = new ArrayList<>();

            for (RequestProductOrderDTO productDTO:requestProductOrderDTOList
                 ) {

                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPedido(newPedido);
                Menu foundMenu = menuRepository.findById(productDTO.getProductId()).orElseThrow( ()-> new EntityNotFoundException("No se encontraron menus con el ID enviado"));
                detallePedido.setMenu(foundMenu);
                detallePedido.setCantidad(productDTO.getQuantity());
                detallePedidos.add(detallePedido);
            }
            newPedido.setDetalles(detallePedidos);



            newPedido.setRestaurante(foundRestaurant);
            newPedido.setLocal(foundLocation);
            newPedido.setUsuario(foundUser);
            newPedido.setHoraEntregaSolicitada(requestDTO.getRequestDateTime());

            BigDecimal costoTotal = PedidoServiceImp.CalcularCostoPedido(detallePedidos, foundUser, newPedido.getHoraEntregaSolicitada());
            newPedido.setCostoTotal(costoTotal);



            pedidoRepository.save(newPedido);

        }

        catch (Exception ex)

        {
            throw ex;

        }




        return responseOrderDTO;

    }




    public static BigDecimal CalcularCostoPedido(List<DetallePedido> detallePedidos, Usuario usuario, LocalDateTime horaSolicitada)
    {

        double costoServicio=0;
        double costoEnvio=0;
        BigDecimal costoTotalProductos = new BigDecimal(0);



        for (DetallePedido detallePedido: detallePedidos)
        {

            BigDecimal bigDecimalCantidad = new BigDecimal(detallePedido.getCantidad());
            BigDecimal bigDecimalCostoDetalle = detallePedido.getMenu().getPrecio().multiply( bigDecimalCantidad );
            costoTotalProductos.add(bigDecimalCostoDetalle);

        }


        //Setear el valor de costoServicio segun nivel usuario
        switch (usuario.getNivel())
        {
            case 1:
                costoServicio = 100;
                break;
            case 2:
                costoServicio=100;
                break;

            case 3:
                costoServicio=50;
                break;
            case 4:
                costoServicio=50;
                break;
            case 5:
                costoServicio=0;
                break;
        };

        //Calcular costo del envio segun hora pico o no

        //Obtengo el dia de hoy
        LocalDate today = LocalDate.now();

        //Seteo intervalos horarios pico diurno y nocturno

        LocalDateTime daytimeRushHourStart = LocalDateTime.of(today, LocalTime.of(12, 0) );
        LocalDateTime daytimeRushHourEnd = LocalDateTime.of(today, LocalTime.of(14, 0));

        LocalDateTime nighttimeRushHourStart = LocalDateTime.of(today, LocalTime.of(20, 0));
        LocalDateTime nighttimeRushHourEnd = LocalDateTime.of(today, LocalTime.of(22, 0));

        //Antes del dtmRushStart--100 pesos (previo hora pico diurna)
        //Despues del dtmRushEnd y antes del ntmRushStart --100 pesos (despues de la hora pico diurna y antes de la hora pico nocturna)

        //Despues del dtmRushStart y antes del dtmRushhourEnd -- 200 pesos (durante la hora pico diurna)
        //Despues del ntmRushStart y antes del ntmRushEnd  -- 200 pesos (durante la hora pico nocturna)



        if (horaSolicitada.isBefore(daytimeRushHourStart)){
            costoEnvio=100;
        }

        if(horaSolicitada.isAfter(daytimeRushHourEnd) && horaSolicitada.isBefore(nighttimeRushHourStart) )
        {
            costoEnvio=100;
        }

        if(horaSolicitada.isAfter(daytimeRushHourStart) && horaSolicitada.isBefore(daytimeRushHourEnd) )
        {
            costoEnvio=200;
        }

        if(horaSolicitada.isAfter(nighttimeRushHourStart) && horaSolicitada.isBefore(nighttimeRushHourEnd))
        {
            costoEnvio=200;
        }

        BigDecimal bigDecimalCostoServicio = new BigDecimal(costoServicio);
        BigDecimal bigDecimalCostoEnvio = new BigDecimal(costoEnvio);
        BigDecimal totalTotal = costoTotalProductos.add(bigDecimalCostoEnvio).add(bigDecimalCostoServicio);

        return totalTotal;



    }



}
