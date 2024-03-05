package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.Builder;

@Builder
public class RequestProductOrderDTO {

    private Long productId;

    private Integer quantity;
}
