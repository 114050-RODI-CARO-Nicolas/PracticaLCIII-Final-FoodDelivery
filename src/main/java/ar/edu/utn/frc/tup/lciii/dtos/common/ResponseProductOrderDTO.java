package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductOrderDTO {

    private Integer quantity;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_price")
    private BigDecimal productPrice;

    @JsonProperty("total_product_amount")
    private BigDecimal totalProductAmount;
}
