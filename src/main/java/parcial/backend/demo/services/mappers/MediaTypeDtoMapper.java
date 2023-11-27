package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.MediaType;
import parcial.backend.demo.entities.dto.MediaTypeDto;

import java.util.function.Function;

@Service
public class MediaTypeDtoMapper implements Function<MediaType, MediaTypeDto> {
    @Override
    public MediaTypeDto apply(MediaType m) {
        try {
            return new MediaTypeDto(m.getId(), m.getName());
        } catch (NullPointerException e) {
            return null;
        }
    }
}
