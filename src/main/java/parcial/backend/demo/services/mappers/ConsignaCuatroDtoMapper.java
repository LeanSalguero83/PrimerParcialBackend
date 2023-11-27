package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ConsignaCuatroDto;

import java.util.function.Function;

@Service
public class ConsignaCuatroDtoMapper implements Function<Track, ConsignaCuatroDto> {

    @Override
    public ConsignaCuatroDto apply(Track t) {
        return new ConsignaCuatroDto(
                t.getId(),
                t.getName(),
                t.getComposer(),
                t.getMilliseconds()
        );
    }
}
