package parcial.backend.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Playlist;
import parcial.backend.demo.entities.PlaylistTrack;
import parcial.backend.demo.entities.PlaylistTrackId;
import parcial.backend.demo.entities.Track;
import parcial.backend.demo.entities.dto.PlaylistTrackDto;
import parcial.backend.demo.repositories.PlaylistRepository;
import parcial.backend.demo.repositories.PlaylistTrackRepository;
import parcial.backend.demo.repositories.TrackRepository;
import parcial.backend.demo.services.mappers.PlaylistTrackDtoMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlaylistTrackServiceImpl implements PlaylistTrackService{

    @Autowired
    private final PlaylistRepository playlistRepository;
    @Autowired
    private final TrackRepository trackRepository;
    @Autowired
    private final PlaylistTrackRepository playlistTrackRepository;
    private final PlaylistTrackDtoMapper playlistTrackDtoMapper;

    public PlaylistTrackServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository, PlaylistTrackRepository playlistTrackRepository, PlaylistTrackDtoMapper playlistTrackDtoMapper) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.playlistTrackRepository = playlistTrackRepository;
        this.playlistTrackDtoMapper = playlistTrackDtoMapper;
    }


    @Override
    public PlaylistTrackDto add(PlaylistTrackDto entity) {
        PlaylistTrack p = new PlaylistTrack();
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(entity.getPlaylist().getId());
        Optional<Track> optionalTrack = trackRepository.findById(entity.getTrack().getId());

        if (optionalTrack.isEmpty() || optionalPlaylist.isEmpty()) {
            throw new NoSuchElementException("No se encontró la playlist / track");
        }

        p.setPlaylist(optionalPlaylist.get());
        p.setTrack(optionalTrack.get());

        PlaylistTrack nuevo = playlistTrackRepository.save(p);

        return playlistTrackDtoMapper.apply(nuevo);

    }

    @Override
    public PlaylistTrackDto update(Long id, PlaylistTrackDto entity) {
        return null;
    }


    @Override
    public PlaylistTrackDto delete(PlaylistTrackId id) {
        Optional<PlaylistTrack> p = playlistTrackRepository.findById(id);

        if (p.isEmpty()) {
            throw new NoSuchElementException("No existe");
        }
        PlaylistTrack playlistTrack = p.get();
        playlistTrackRepository.delete(playlistTrack);
        return playlistTrackDtoMapper.apply(playlistTrack);
    }

    @Override
    public PlaylistTrackDto getById(PlaylistTrackId id) {
        Optional<PlaylistTrack> p = playlistTrackRepository.findById(id);

        if (p.isEmpty()) {
            throw new NoSuchElementException("No existe");
        }
        return playlistTrackDtoMapper.apply(p.get());
    }

    @Override
    public List<PlaylistTrackDto> getAll() {
        List<PlaylistTrack> playlistTracks = playlistTrackRepository.findAll();
        return playlistTracks
                .stream()
                .map(playlistTrackDtoMapper)
                .toList();
    }

    @Override
    public PlaylistTrackId obtenerId(Long trackId, Long playlistId) {
        Optional<Track> optionalTrack = trackRepository.findById(trackId);
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);

        if (optionalTrack.isEmpty() || optionalPlaylist.isEmpty()) {
            throw new NoSuchElementException("No existe esta id");
        }

        return new PlaylistTrackId(optionalTrack.get(), optionalPlaylist.get());
    }
}
