package parcial.backend.demo.services;

import org.springframework.data.jpa.repository.Query;
import parcial.backend.demo.entities.Album;
import parcial.backend.demo.entities.Artist;
import parcial.backend.demo.entities.dto.AlbumDto;

import java.util.List;

public interface AlbumService extends Service<AlbumDto, Long> {
}
