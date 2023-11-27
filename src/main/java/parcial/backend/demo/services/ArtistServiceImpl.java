package parcial.backend.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.Album;
import parcial.backend.demo.entities.Artist;
import parcial.backend.demo.entities.dto.ArtistDto;
import parcial.backend.demo.repositories.AlbumRepository;
import parcial.backend.demo.repositories.ArtistRepository;
import parcial.backend.demo.services.mappers.ArtistDtoMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{
    @Autowired
    private final ArtistRepository artistRepository;
    private final ArtistDtoMapper mapper;

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistDtoMapper mapper) {
        this.artistRepository = artistRepository;
        this.mapper = mapper;
    }
    @Override
    public ArtistDto add(ArtistDto entity) {
        Optional<Artist> existe = artistRepository.findByName(entity.getName());
        if (existe.isPresent()) {
            throw new DataIntegrityViolationException("Ya existe ese artista");
        }
        Artist a = new Artist();
        a.setName(entity.getName());
        artistRepository.save(a);
        return mapper.apply(a);
    }
    @Override
    public ArtistDto update(Long id, ArtistDto entity) {
        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("No se encontró el artista " + id);
        }
        Artist artist = optionalArtist.get();
        artist.setName(entity.getName());
        artistRepository.save(artist);
        return mapper.apply(artist);
    }

    @Override
    public ArtistDto delete(Long aLong) {
        Optional<Artist> optionalArtist = artistRepository.findById(aLong);
        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("No se encontró el artista " + aLong);
        }
        Artist artist = optionalArtist.get();
        artistRepository.delete(artist);
        return mapper.apply(artist);
    }

    @Override
    public ArtistDto getById(Long aLong) {
        Optional<Artist> optionalArtist = artistRepository.findById(aLong);

        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("No se encontró el artista " + aLong);
        }

        return mapper.apply(optionalArtist.get());
    }
    @Override
    public List<ArtistDto> getAll() {
        List<Artist> artists = artistRepository.findAll();
        return artists
                .stream()
                .map(mapper)
                .toList();
    }
}
