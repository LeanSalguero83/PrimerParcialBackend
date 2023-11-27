package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
