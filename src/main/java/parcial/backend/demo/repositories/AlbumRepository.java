package parcial.backend.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import parcial.backend.demo.entities.Album;
import parcial.backend.demo.entities.Artist;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    //@Query("SELECT a FROM Album a WHERE a.artist = :a")
    //List<Album> findByArtist(Artist a);
    List<Album> findByTitle(String title);
}
