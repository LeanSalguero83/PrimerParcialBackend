package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.PlaylistTrack;
import parcial.backend.demo.entities.dto.PlaylistDto;
import parcial.backend.demo.entities.dto.PlaylistTrackDto;

import java.util.NoSuchElementException;
import java.util.function.Function;

@Service
public class PlaylistTrackDtoMapper implements Function<PlaylistTrack, PlaylistTrackDto> {
    private final TrackNameDtoMapper trackNameDtoMapper;
    private final PlaylistDtoMapper playlistDtoMapper;


    public PlaylistTrackDtoMapper(TrackNameDtoMapper trackNameDtoMapper, PlaylistDtoMapper playlistDtoMapper) {
        this.trackNameDtoMapper = trackNameDtoMapper;
        this.playlistDtoMapper = playlistDtoMapper;
    }

    @Override
    public PlaylistTrackDto apply(PlaylistTrack p) {
        try {
            return new PlaylistTrackDto(
                    playlistDtoMapper.apply(p.getPlaylist()),
                    trackNameDtoMapper.apply(p.getTrack())
            );
        } catch (NullPointerException e) {
            return null;
        }
    }
}
