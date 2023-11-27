package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignaUnoDto {

    private String trackName;
    private String albumTitle;
    private String artistName;
    private long milliseconds;
}
