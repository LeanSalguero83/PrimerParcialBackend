package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaPlaylistBodyDto {
    private String nombrePlaylist;
    private Long idCliente;
    private String composerFilter;
    private Long duracionMaxima;
}
