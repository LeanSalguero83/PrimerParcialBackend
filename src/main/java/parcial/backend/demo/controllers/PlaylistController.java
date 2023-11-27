package parcial.backend.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.backend.demo.entities.dto.ConsginaDosDto2;
import parcial.backend.demo.entities.dto.ConsignaDosDto;
import parcial.backend.demo.entities.dto.PlaylistDto;
import parcial.backend.demo.services.PlaylistService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAll() {
        List<PlaylistDto> playlists = playlistService.getAll();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getById(@PathVariable Long id) {
        try {
            PlaylistDto p = playlistService.getById(id);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaylistDto> update(@PathVariable Long id, @RequestBody PlaylistDto playlist) {
        try {
            PlaylistDto p = playlistService.update(id, playlist);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlaylistDto> delete(@PathVariable Long id) {
        try {
            PlaylistDto p = playlistService.delete(id);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<ConsignaDosDto> add(@RequestBody ConsginaDosDto2 playlist) {
        try {
            ConsignaDosDto p = playlistService.crear(playlist);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(204).build();
        }
    }


}
