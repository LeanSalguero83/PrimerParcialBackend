package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaPlayListBodyDtoRecuperatorio {
    private String nombrePlaylist;
    private Long idCliente;
    private long idArtist;
    private Long duracionMaxima;



}
