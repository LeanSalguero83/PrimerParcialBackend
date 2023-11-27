package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import parcial.backend.demo.entities.Artist;
import parcial.backend.demo.entities.Genre;
import parcial.backend.demo.entities.Track;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
}
