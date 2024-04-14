package ar.edu.utn.frc.tup.lciii.services.implementations;

import ar.edu.utn.frc.tup.lciii.domain.*;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RequestProductOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseOrderDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.ResponseProductOrderDTO;
import ar.edu.utn.frc.tup.lciii.model.RushHour;
import ar.edu.utn.frc.tup.lciii.repositories.*;
import ar.edu.utn.frc.tup.lciii.services.IPedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

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

    @Autowired
    BarrioRepository barrioRepository;

    @Autowired
    ObjectMapper objectMapper;


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

            BigDecimal costoTotal = PedidoServiceImp.CalcularCostoTotalPedido(newPedido.getUsuario(), newPedido.getHoraEntregaSolicitada(), newPedido.getDetalles());
            newPedido.setCostoTotal(costoTotal);

         if(this.ChequearDisponibilidadPorMaxPedidosConcurrentes(foundLocation, newPedido.getHoraEntregaSolicitada() ) == false )
         {
             throw new CancellationException("El pedido no puede ser procesado porque el local no puede manejar cierta cantidad de pedidos al mismo tiempo");
         }

         if ( this.ChequarDisponibilidadBarrio(foundUser, foundLocation )==false )
         {
             throw new CancellationException("El local solicitado no opera en el barrio del cliente");
         }

         if(this.ChequearDisponibilidadHoraria(newPedido, foundLocation) == false )
         {
             throw new CancellationException("El pedido no puede ser procesado porque el local no opera a la hora solicitada");
         }
            pedidoRepository.save(newPedido);

         responseOrderDTO.setId(newPedido.getId());

         responseOrderDTO.setRestaurantName(newPedido.getRestaurante().getNombre());
         responseOrderDTO.setLocationName(newPedido.getLocal().getNombre());
         //Tal vez esta property de abajo tambien deberia estar en la clase de dominio
         responseOrderDTO.setEstimatedDeliveryDateTime(PedidoServiceImp.ObtenerHorarioEstimado(newPedido.getDetalles(), newPedido.getHoraEntregaSolicitada()));

         responseOrderDTO.setTotalAmountOrder(newPedido.getCostoTotal());
         //Tal vez estas 3 properties de abajo tambien deberia estar en la clase de dominio
         responseOrderDTO.setServiceCostAmount(PedidoServiceImp.CalcularCostoServicio(newPedido.getUsuario()));
         responseOrderDTO.setDeliveryAmount(PedidoServiceImp.CalcularCostoEnvioSegunHoraPico(newPedido.getHoraEntregaSolicitada()));
         responseOrderDTO.setTotalProductsAmount(PedidoServiceImp.CalcularCostoTotalProductos(newPedido.getDetalles()));


         List<ResponseProductOrderDTO> responseProductOrderDTOList = new ArrayList<>();

         for (DetallePedido detalle: newPedido.getDetalles())
         {
            ResponseProductOrderDTO responseProductOrderDTO = new ResponseProductOrderDTO();
            responseProductOrderDTO.setProductName(detalle.getMenu().getNombre());
            responseProductOrderDTO.setProductPrice(detalle.getMenu().getPrecio());
            responseProductOrderDTO.setQuantity(detalle.getCantidad());
            responseProductOrderDTO.setTotalProductAmount( detalle.getMenu().getPrecio().multiply(new BigDecimal( detalle.getCantidad()) ) );
            responseProductOrderDTOList.add(responseProductOrderDTO);

         }
         responseOrderDTO.setProducts(responseProductOrderDTOList);






        }



        catch (RuntimeException ex)
        {
            throw ex;
        }

        catch (Exception ex)
        {
            throw ex;

        }
        return responseOrderDTO;
    }


    ////////////////PROCESS UTILITIES

    public  boolean ChequearDisponibilidadPorMaxPedidosConcurrentes(Local requestedLocation, LocalDateTime horaSolicitada)
    {
        Integer maxConcurrentOrders = requestedLocation.getNumeroPedidosMismoTiempo();
        List<Pedido> concurrentOrders = pedidoRepository.findByhoraEntregaSolicitada(horaSolicitada);

        return maxConcurrentOrders>concurrentOrders.size();

    };

    public boolean ChequearDisponibilidadHoraria(Pedido pedido, Local requestedLocation)  {
        String jsonString = requestedLocation.getOperationHoursJsonData();

        try {
            RushHour[] rushHours = objectMapper.readValue(jsonString, RushHour[].class);

            //Obtener el List<RushHour>, donde hay un DOW del enum universal, y las horas y minutos del START y las del END son Integer

            //Comparar el dia del pedido con cada dia de la lista rushHour

            ////Esta mal la siguiente comprobacion
            // porque si el local tiene tres horarios de atencion, ya si el primero no coincide con el horario solicitado
            // se retorna false y no se vuelve a iterar.



            LocalDateTime horaEntregaSolicitada = pedido.getHoraEntregaSolicitada();
            for (RushHour horarioAtencion : rushHours
            ) {
                if(horaEntregaSolicitada.getDayOfWeek() == horarioAtencion.getDay())
                {
                    //EL dia de atencion del local coincide con el dia del pedido

                    Integer horaApertura = horarioAtencion.getStartHour();
                    Integer minutosApertura = horarioAtencion.getStartMinute();

                    Integer horaCierre = horarioAtencion.getEndHour();
                    Integer minutosCierre = horarioAtencion.getEndMinute();

                    LocalTime horarioApertura =  LocalTime.of(horaApertura, minutosApertura);
                    LocalTime horarioCierre = LocalTime.of(horaCierre, minutosCierre);

                    LocalTime horaEntregaSolicitadaSinDia = horaEntregaSolicitada.toLocalTime();

                    if(horaEntregaSolicitadaSinDia.isAfter(horarioApertura) && horaEntregaSolicitadaSinDia.isBefore(horarioCierre))
                    {
                        return true;
                    }

                }
                //El dia solicitado no se atiende en el local;

            }
            return false;
        }

        catch (JsonProcessingException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }


    };

    public boolean ChequarDisponibilidadBarrio(Usuario usuario, Local requestedLocation)
    {
      Barrio barrioUser = usuario.getBarrio();
      Barrio barrioLocalSolicitado = requestedLocation.getBarrio();

      return barrioUser.equals(barrioLocalSolicitado);

    };



    public static LocalDateTime ObtenerHorarioEstimado(List<DetallePedido> detallePedidos, LocalDateTime horaSolicitada){


        //Obtener lista de valores de minutos preparacion en lista menus

        List<Integer> listaMinutosPreparacion = new ArrayList<>();
        for (DetallePedido detalle: detallePedidos
             ) {
            listaMinutosPreparacion.add(detalle.getMenu().getMinutosPreparacion());
        };

        //Obtener producto de la lista de minutosPrep que mas demora en prepararse

        if ( listaMinutosPreparacion.size()==0){
            throw new RuntimeException("prep time list of values should not be empty");
        }



        Integer maxNumber;

          maxNumber = listaMinutosPreparacion.get(0);

          if (listaMinutosPreparacion.size()>1)
          {
              for(int i = 1; i < listaMinutosPreparacion.size(); i++){
                  int currentNumber = listaMinutosPreparacion.get(i);
                  if(currentNumber>maxNumber)
                  {
                      maxNumber = currentNumber;
                  }
              }

          }


       LocalDateTime horarioEstimado = horaSolicitada.plusMinutes(maxNumber);
        horarioEstimado.plusMinutes(15);
        return horarioEstimado;

    }






    public static BigDecimal CalcularCostoEnvioSegunHoraPico(LocalDateTime horaSolicitada)
    {

        BigDecimal costoEnvio = BigDecimal.ZERO;

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
            costoEnvio = new BigDecimal(100);
        }

        if(horaSolicitada.isAfter(daytimeRushHourEnd) && horaSolicitada.isBefore(nighttimeRushHourStart) )
        {
            costoEnvio = new BigDecimal(100);
        }

        if(horaSolicitada.isAfter(daytimeRushHourStart) && horaSolicitada.isBefore(daytimeRushHourEnd) )
        {
            costoEnvio = new BigDecimal(200);
        }

        if(horaSolicitada.isAfter(nighttimeRushHourStart) && horaSolicitada.isBefore(nighttimeRushHourEnd))
        {
            costoEnvio = new BigDecimal(200);
        };
        return costoEnvio;

    }


    public static BigDecimal CalcularCostoServicio(Usuario usuario)
    {
        BigDecimal costoServicio=new BigDecimal(0);

        //Setear el valor de costoServicio segun nivel usuario
        switch (usuario.getNivel())
        {
            case 1:
                costoServicio = new BigDecimal(100);
                break;
            case 2:
               costoServicio = new BigDecimal(100);
                break;

            case 3:
                costoServicio = new BigDecimal(50);
                break;
            case 4:
                costoServicio = new BigDecimal(50);
                break;
            case 5:
                costoServicio = new BigDecimal(0);
                break;
        };
        return costoServicio;
    };


    public static BigDecimal CalcularCostoTotalProductos(List<DetallePedido> detallePedidos)
    {
        BigDecimal costoTotalProductos = BigDecimal.ZERO;
        for (DetallePedido detallePedido: detallePedidos)
        {

            BigDecimal bigDecimalCantidad = new BigDecimal(detallePedido.getCantidad());
            BigDecimal bigDecimalCostoDetalle = detallePedido.getMenu().getPrecio().multiply( bigDecimalCantidad );
            costoTotalProductos = costoTotalProductos.add(bigDecimalCostoDetalle);

        };
        return costoTotalProductos;


    }

    public static BigDecimal CalcularCostoTotalPedido(Usuario usuario, LocalDateTime horaSolicitada, List<DetallePedido> detallePedidos)
    {

        //Descomponer esta funcion en 3 partes distintas para reutilizarla arriba y despues tener una funcion wrapper que ejecute las 3 partes anteriores

        BigDecimal costoTotalPedido = BigDecimal.ZERO;


        BigDecimal bigDecimalCostoServicio = PedidoServiceImp.CalcularCostoServicio(usuario);
        BigDecimal bigDecimalCostoEnvio = PedidoServiceImp.CalcularCostoEnvioSegunHoraPico(horaSolicitada);
        BigDecimal costoTotalProductos = PedidoServiceImp.CalcularCostoTotalProductos(detallePedidos);

         costoTotalPedido = costoTotalPedido.add(bigDecimalCostoServicio).add(bigDecimalCostoEnvio).add(costoTotalProductos)  ;
        return costoTotalPedido;
    } ;



};
