package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
