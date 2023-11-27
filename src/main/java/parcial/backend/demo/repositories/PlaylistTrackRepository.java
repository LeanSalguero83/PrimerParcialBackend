package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.PlaylistTrack;
import parcial.backend.demo.entities.PlaylistTrackId;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {
}
