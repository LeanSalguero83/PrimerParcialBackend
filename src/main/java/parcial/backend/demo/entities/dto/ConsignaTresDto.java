package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignaTresDto {
    private long trackId;
    private String name;
    private String composer;
    private long milliseconds;

}
