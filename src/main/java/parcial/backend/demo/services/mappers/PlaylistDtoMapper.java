package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.dto.PlaylistDto;

import java.util.function.Function;

@Service
public class PlaylistDtoMapper implements Function<Playlist, PlaylistDto> {

    @Override
    public PlaylistDto apply(Playlist p) {
        try {
            return new PlaylistDto(p.getId(), p.getName());

        } catch (NullPointerException e) {
            return null;
        }
    }
}
