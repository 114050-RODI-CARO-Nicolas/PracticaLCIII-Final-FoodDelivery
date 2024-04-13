package ar.edu.utn.frc.tup.lciii.dtos.common;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductOrderDTO {

    private Long productId;

    private Integer quantity;
}
