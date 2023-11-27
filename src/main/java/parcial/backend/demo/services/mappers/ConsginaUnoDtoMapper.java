package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ConsignaUnoDto;
import parcial.backend.demo.entities.dto.TrackDto;

import java.util.function.Function;

@Service
public class ConsginaUnoDtoMapper implements Function<Track, ConsignaUnoDto> {

    @Override
    public ConsignaUnoDto apply(Track t) {
        try {
            return new ConsignaUnoDto(
                    t.getId(),
                    t.getName(),
                    t.getAlbum().getTitle(),
                    t.getAlbum().getArtist().getName(),
                    t.getMilliseconds()
            );

        } catch (NullPointerException e) {
            return null;
        }
    }
}
