package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaPlaylistBodyDtoJUEVES {
    private String nombrePlaylist;
    private Long idCliente;
    private Long idGenre;
    private String composerFilter;
    private Long duracionMaxima;



}
