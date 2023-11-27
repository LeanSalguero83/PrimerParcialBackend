package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Album;
import parcial.backend.demo.entities.dto.AlbumDto;

import java.util.function.Function;

@Service
public class AlbumDtoMapper implements Function<Album, AlbumDto> {
    private final ArtistDtoMapper artistDtoMapper;
    public AlbumDtoMapper(ArtistDtoMapper artistDtoMapper) {
        this.artistDtoMapper = artistDtoMapper;
    }
    @Override
    public AlbumDto apply(Album a) {
        try {
            return new AlbumDto(
                    a.getId(),
                    a.getTitle(),
                    artistDtoMapper.apply(a.getArtist()));
        } catch (NullPointerException e) {
            return null;
        }
    }
}
