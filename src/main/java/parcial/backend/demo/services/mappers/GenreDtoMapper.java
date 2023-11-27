package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Genre;
import parcial.backend.demo.entities.dto.GenreDto;

import java.util.function.Function;

@Service
public class GenreDtoMapper implements Function<Genre, GenreDto> {
    @Override
    public GenreDto apply(Genre g) {
        try {
            return new GenreDto(g.getId(), g.getName());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
