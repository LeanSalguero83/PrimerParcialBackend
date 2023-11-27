package parcial.backend.demo.services;

import parcial.backend.demo.entities.dto.*;

import java.util.List;

public interface TrackService extends Service<TrackDto, Long> {
    List<ConsignaUnoDto> getAllFiltrado(Long idArtist, Long idGenre);

    List<ConsignaTresDto> getAllTracksByCustomerId(Long customerId);

    List<ConsignaCuatroDto> getTracksByCustomerIdAndGenre(Long customerId, Long genreId);

    List<ConsignaCincoDto> getTracksByCustomerIdAndArtist(Long customerId, Long artistId);


    }
