package parcial.backend.demo.services.mappers;

import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ConsignaCincoDto;

import java.util.function.Function;

@Service
public class ConsignaCincoDtoMapper implements Function<Track, ConsignaCincoDto> {
    @Override
    public ConsignaCincoDto apply(Track track) {
        return new ConsignaCincoDto(
                track.getId(),
                track.getName(),
                track.getMilliseconds() / 1000, // Convertir de milisegundos a segundos
                track.getUnitPrice()
        );
    }
}
