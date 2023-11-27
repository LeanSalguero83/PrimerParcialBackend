package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ConsignaTresDto;

import java.util.function.Function;

@Service
public class ConsignaTresDtoMapper implements Function<Track, ConsignaTresDto> {

    @Override
    public ConsignaTresDto apply(Track t) {
        try {
            return new ConsignaTresDto(
                    t.getId(),
                    t.getName(),
                    t.getComposer(),
                    t.getMilliseconds()
            );
        } catch (NullPointerException e) {
            return null;
        }
    }
}