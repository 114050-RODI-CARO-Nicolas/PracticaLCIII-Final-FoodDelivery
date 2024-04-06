package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrderDTO {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("location_id")
    private Long locationId;

    private List<RequestProductOrderDTO> products;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("request_date_time")
    private LocalDateTime requestDateTime;

}
