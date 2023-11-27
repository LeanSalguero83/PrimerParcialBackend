package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignaCincoDto {
    private Long TrackId;
    private String Name;
    private Long Segundos;
    private Double UnitPrice;
}
