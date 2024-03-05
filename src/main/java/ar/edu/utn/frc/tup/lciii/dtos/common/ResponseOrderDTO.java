package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ResponseOrderDTO {

    private Long id;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("location_name")
    private Long locationName;

    private List<ResponseProductOrderDTO> products;

    @JsonProperty("total_products_amount")
    private BigDecimal totalProductsAmount;

    @JsonProperty("delivery_amount")
    private BigDecimal deliveryAmount;

    @JsonProperty("service_cost_amount")
    private BigDecimal serviceCostAmount;

    @JsonProperty("total_amount")
    private BigDecimal totalAmountOrder;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("estimated_delivery_date_time")
    private LocalDateTime estimatedDeliveryDateTime;
}
