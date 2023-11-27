package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.TrackDto;
import parcial.backend.demo.entities.dto.TrackNameDto;

import java.util.function.Function;

@Service
public class TrackNameDtoMapper implements Function<Track, TrackNameDto> {
    @Override
    public TrackNameDto apply(Track t) {
        try {
            return new TrackNameDto(t.getId(), t.getName());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
