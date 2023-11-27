package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.TrackDto;

import java.util.function.Function;

@Service
public class TrackDtoMapper implements Function<Track, TrackDto> {
    private final AlbumDtoMapper albumDtoMapper;
    private final MediaTypeDtoMapper mediaTypeDtoMapper;
    private final GenreDtoMapper genreDtoMapper;

    public TrackDtoMapper(AlbumDtoMapper albumDtoMapper, MediaTypeDtoMapper mediaTypeDtoMapper, GenreDtoMapper genreDtoMapper) {
        this.albumDtoMapper = albumDtoMapper;
        this.mediaTypeDtoMapper = mediaTypeDtoMapper;
        this.genreDtoMapper = genreDtoMapper;
    }

    @Override
    public TrackDto apply(Track t) {
        try {
            return new TrackDto(
                    t.getId(),
                    t.getName(),
                    albumDtoMapper.apply(t.getAlbum()),
                    mediaTypeDtoMapper.apply(t.getMediaType()),
                    genreDtoMapper.apply(t.getGenre()),
                    t.getComposer(),
                    t.getMilliseconds(),
                    t.getBytes(),
                    t.getUnitPrice()
            );
        } catch (NullPointerException e) {
            return null;
        }
    }
}
