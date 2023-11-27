package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaPlaylistResponseDtoRECUPERATORIO {
    private String nombrePlaylist;
    private Long duracionTotal;
    private Double costoTotal;
    private List<TrackNameDto> tracks;
}
