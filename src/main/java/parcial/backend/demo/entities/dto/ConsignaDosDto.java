package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignaDosDto {
    private String name;
    private long duracion;
    private List<TrackNameDto> tracks;
}
