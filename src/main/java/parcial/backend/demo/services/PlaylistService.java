package parcial.backend.demo.services;

import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.dto.*;

public interface PlaylistService extends Service<PlaylistDto, Long>{
    ConsignaDosDto crear(ConsginaDosDto2 entity);
    NuevaPlaylistResponseDto crear2(NuevaPlaylistBodyDto nuevaPlaylistBodyDto);

    NuevaPlaylistResponseDto crear3(NuevaPlaylistBodyDtoJUEVES dtoJUEVES);

    NuevaPlaylistResponseDtoRECUPERATORIO crear4(NuevaPlayListBodyDtoRecuperatorio dtoRECUPERATORIO);


}
