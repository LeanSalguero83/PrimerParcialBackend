package parcial.backend.demo.services.mappers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.NuevaPlaylistResponseDtoRECUPERATORIO;
import parcial.backend.demo.entities.dto.TrackNameDto;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NuevaPlaylistResponseDtoRECUPERATORIOMapper implements Function<Playlist, NuevaPlaylistResponseDtoRECUPERATORIO> {
    TrackNameDtoMapper trackNameDtoMapper;

    @Override
    public NuevaPlaylistResponseDtoRECUPERATORIO apply(Playlist playlist) {
        List<Track> tracks = playlist.getTracks();
        long duracionTotalEnSegundos = tracks
                .stream()
                .mapToLong(Track::getMilliseconds)
                .sum() / 1000;  // Convertir milisegundos a segundos
        double costoTotal = tracks
                .stream()
                .mapToDouble(Track::getUnitPrice)
                .sum();
        List<TrackNameDto> tracksOrdenados = tracks.stream()
                .sorted(Comparator.comparing(track -> track.getAlbum().getTitle()))
                .map(trackNameDtoMapper)
                .collect(Collectors.toList());

        return new NuevaPlaylistResponseDtoRECUPERATORIO(
                playlist.getName(),
                duracionTotalEnSegundos,
                costoTotal,
                tracksOrdenados);
    }
}
