package parcial.backend.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.backend.demo.entities.dto.ArtistDto;
import parcial.backend.demo.services.ArtistService;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistDto>> getAll() {
        List<ArtistDto> artists = artistService.getAll();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getById(@PathVariable Long id) {
        try {
            ArtistDto artist = artistService.getById(id);
            return ResponseEntity.ok(artist);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ArtistDto> update(@PathVariable Long id, @RequestBody ArtistDto artist) {
        try {
            ArtistDto nuevo = artistService.update(id, artist);
            return ResponseEntity.ok(nuevo);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtistDto> delete(@PathVariable Long id) {
        try {
            ArtistDto artist = artistService.delete(id);
            return ResponseEntity.ok(artist);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<ArtistDto> add(@RequestBody ArtistDto artist) {
        try {
            ArtistDto nuevo = artistService.add(artist);
            return ResponseEntity.ok(nuevo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
