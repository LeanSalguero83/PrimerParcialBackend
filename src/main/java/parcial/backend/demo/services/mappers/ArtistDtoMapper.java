package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Artist;
import parcial.backend.demo.entities.dto.ArtistDto;
import java.util.function.Function;

@Service
public class ArtistDtoMapper implements Function<Artist, ArtistDto> {
    @Override
    public ArtistDto apply(Artist a) {
        try {
            return new ArtistDto(a.getId(), a.getName());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
