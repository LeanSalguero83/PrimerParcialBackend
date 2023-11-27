package parcial.backend.demo.services;

import parcial.backend.demo.entities.PlaylistTrack;
import parcial.backend.demo.entities.PlaylistTrackId;
import parcial.backend.demo.entities.dto.PlaylistTrackDto;

public interface PlaylistTrackService extends Service<PlaylistTrackDto, PlaylistTrackId>{

    PlaylistTrackId obtenerId(Long trackId, Long playlistId);
}
