package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import parcial.backend.demo.entities.Track;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaPlaylistResponseDto {
    private String nombrePlaylist;
    private Long duracionTotal;
    private List<TrackNameDto> tracks;
}
