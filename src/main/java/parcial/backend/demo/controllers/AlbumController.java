package parcial.backend.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.backend.demo.entities.Album;
import parcial.backend.demo.entities.dto.AlbumDto;
import parcial.backend.demo.services.AlbumService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAll() {
        List<AlbumDto> albums = albumService.getAll();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getById(@PathVariable Long id) {
        try {
            AlbumDto album = albumService.getById(id);
            return ResponseEntity.ok(album);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlbumDto> update(@PathVariable Long id, @RequestBody AlbumDto album) {
        try {
            AlbumDto a = albumService.update(id, album);
            return ResponseEntity.ok(a);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlbumDto> delete(@PathVariable Long id) {
        try {
            AlbumDto a = albumService.delete(id);
            return ResponseEntity.ok(a);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<AlbumDto> add(@RequestBody AlbumDto album) {
        AlbumDto a = albumService.add(album);
        return ResponseEntity.ok(a);
    }
}

