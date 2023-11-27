package parcial.backend.demo.services.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.NuevaPlaylistResponseDto;
import java.util.List;
import java.util.function.Function;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor


public class NuevaPlaylistResponseDtoMapper implements Function<Playlist, NuevaPlaylistResponseDto> {
    TrackNameDtoMapper trackNameDtoMapper;

    @Override
    public NuevaPlaylistResponseDto apply(Playlist playlist) {
        List<Track> tracks = playlist.getTracks();
        long duracionTotal = tracks
                .stream()
                .mapToLong(Track::getMilliseconds)
                .sum();

        return new NuevaPlaylistResponseDto(
                playlist.getName(),
                duracionTotal,
                tracks.stream().map(trackNameDtoMapper).toList()
        );
    }
}
