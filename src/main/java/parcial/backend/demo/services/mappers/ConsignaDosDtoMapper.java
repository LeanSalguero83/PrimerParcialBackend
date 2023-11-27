package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ConsignaDosDto;
import parcial.backend.demo.entities.dto.ConsignaUnoDto;

import java.util.List;
import java.util.function.Function;

@Service
public class ConsignaDosDtoMapper implements Function<Playlist, ConsignaDosDto> {

    private final TrackNameDtoMapper trackNameDtoMapper;

    public ConsignaDosDtoMapper(TrackNameDtoMapper trackNameDtoMapper) {
        this.trackNameDtoMapper = trackNameDtoMapper;
    }


    @Override
    public ConsignaDosDto apply(Playlist p) {
        try {
            long duracion = 0;
            List<Track> tracks = p.getTracks();
            for (Track t : tracks) {
                duracion += t.getMilliseconds();
            }

            return new ConsignaDosDto(
                    p.getName(),
                    duracion,
                    p.getTracks().stream().map(trackNameDtoMapper).toList()
            );
        } catch (NullPointerException e) {
            return null;
        }
    }
}
