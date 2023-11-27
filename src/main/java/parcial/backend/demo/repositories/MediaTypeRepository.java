package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.MediaType;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {
}
