package parcial.backend.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parcial.backend.demo.entities.PlaylistTrack;
import parcial.backend.demo.entities.PlaylistTrackId;
import parcial.backend.demo.entities.dto.PlaylistDto;
import parcial.backend.demo.entities.dto.PlaylistTrackDto;
import parcial.backend.demo.services.PlaylistTrackService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/playlist-tracks")
public class PlaylistTrackController {
    @Autowired
    private PlaylistTrackService playlistTrackService;

    @GetMapping
    public ResponseEntity<List<PlaylistTrackDto>> getAll() {
        List<PlaylistTrackDto> playlistTracks = playlistTrackService.getAll();
        return ResponseEntity.ok(playlistTracks);
    }

    @PostMapping
    public ResponseEntity<PlaylistTrackDto> add(@RequestBody PlaylistTrackDto playlistTrack) {
        try {
            PlaylistTrackDto p = playlistTrackService.add(playlistTrack);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("{trackId}&{playlistId}")
    public ResponseEntity<PlaylistTrackDto> getById(
            @PathVariable Long trackId,
            @PathVariable Long playlistId
    ) {
        try {
            PlaylistTrackId id = playlistTrackService.obtenerId(trackId, playlistId);
            PlaylistTrackDto p = playlistTrackService.getById(id);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("{trackId}&{playlistId}")
    public ResponseEntity<PlaylistTrackDto> delete(
            @PathVariable Long trackId,
            @PathVariable Long playlistId) {
        try {
            PlaylistTrackId id = playlistTrackService.obtenerId(trackId, playlistId);
            PlaylistTrackDto p = playlistTrackService.delete(id);
            return ResponseEntity.ok(p);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
