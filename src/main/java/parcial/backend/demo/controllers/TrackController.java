package parcial.backend.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.ArtistDto;
import parcial.backend.demo.entities.dto.ConsignaUnoDto;
import parcial.backend.demo.entities.dto.TrackDto;
import parcial.backend.demo.services.TrackService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping
    public ResponseEntity<TrackDto> add(@RequestBody TrackDto track) {
        try {
            TrackDto t = trackService.add(track);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
/*
    @GetMapping
    public ResponseEntity<List<TrackDto>> getAll() {
        List<TrackDto> tracks = trackService.getAll();
        return ResponseEntity.ok(tracks);
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<TrackDto> getById(@PathVariable Long id) {
        try {
            TrackDto track = trackService.getById(id);
            return ResponseEntity.ok(track);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrackDto> update(@PathVariable Long id, @RequestBody TrackDto track) {
        try {
            TrackDto t = trackService.update(id, track);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrackDto> delete(@PathVariable Long id) {
        try {
            TrackDto t = trackService.delete(id);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ConsignaUnoDto>> getByArtistAndGenre(
            @RequestParam(name = "artistId", required = false) Long artistId,
            @RequestParam(name = "genreId", required = false) Long genreId) {
        try {
            List<ConsignaUnoDto> tracks = trackService.getAllFiltrado(artistId, genreId);
            return ResponseEntity.ok(tracks);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(204).build();
        }
    }
}
