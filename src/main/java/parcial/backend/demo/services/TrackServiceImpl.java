package parcial.backend.demo.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.*;
import parcial.backend.demo.entities.dto.*;
import parcial.backend.demo.repositories.*;
import parcial.backend.demo.services.mappers.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

public class TrackServiceImpl implements TrackService {
    TrackRepository trackRepository;
    AlbumRepository albumRepository;
    MediaTypeRepository mediaTypeRepository;
    GenreRepository genreRepository;
    ArtistRepository artistRepository;
    TrackDtoMapper trackDtoMapper;
    ConsginaUnoDtoMapper consginaUnoDtoMapper;
    CustomerRepository customerRepository;
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    ConsignaTresDtoMapper consignaTresDtoMapper;
    ConsignaCuatroDtoMapper consignaCuatroDtoMapper;
    ConsignaCincoDtoMapper consignaCincoDtoMapper;


    @Override
    public TrackDto add(TrackDto entity) {
        Track t = new Track();
        t.setName(entity.getName());
        Optional<Album> optionalAlbum = albumRepository.findById(entity.getAlbum().getId());
        if (optionalAlbum.isEmpty()) {
            throw new NoSuchElementException("Ingrese un Album válido");
        }
        optionalAlbum.ifPresent(t::setAlbum);
        Optional<MediaType> optionalMediaType = mediaTypeRepository.findById(entity.getMediaType().getId());
        if (optionalMediaType.isEmpty()) {
            throw new NoSuchElementException("Ingrese un MediaType válido");
        }
        optionalMediaType.ifPresent(t::setMediaType);
        Optional<Genre> optionalGenre = genreRepository.findById(entity.getGenre().getId());
        if (optionalGenre.isEmpty()) {
            throw new NoSuchElementException("Ingrese un género válido");
        }
        optionalGenre.ifPresent(t::setGenre);
        t.setComposer(entity.getComposer());
        t.setMilliseconds(entity.getMilliseconds());
        t.setBytes(entity.getBytes());
        t.setUnitPrice(entity.getUnitPrice());

        Track nuevo = trackRepository.save(t);

        return trackDtoMapper.apply(nuevo);
    }

    @Override
    public TrackDto update(Long id, TrackDto entity) {
        Optional<Track> optionalTrack = trackRepository.findById(id);

        if (optionalTrack.isEmpty()) {
            throw new NoSuchElementException("No se encontró ese track");
        }

        Track t = optionalTrack.get();
        t.setName(entity.getName());
        Optional<Album> optionalAlbum = albumRepository.findById(entity.getAlbum().getId());
        optionalAlbum.ifPresent(t::setAlbum);
        Optional<MediaType> optionalMediaType = mediaTypeRepository.findById(entity.getMediaType().getId());
        optionalMediaType.ifPresent(t::setMediaType);
        Optional<Genre> optionalGenre = genreRepository.findById(entity.getGenre().getId());
        optionalGenre.ifPresent(t::setGenre);
        t.setComposer(entity.getComposer());
        t.setMilliseconds(entity.getMilliseconds());
        t.setBytes(entity.getBytes());
        t.setUnitPrice(entity.getUnitPrice());

        trackRepository.save(t);

        return trackDtoMapper.apply(t);
    }

    @Override
    public TrackDto delete(Long aLong) {
        Optional<Track> optionalTrack = trackRepository.findById(aLong);

        if (optionalTrack.isEmpty()) {
            throw new NoSuchElementException("No se encontró ese track");
        }

        Track t = optionalTrack.get();
        trackRepository.delete(t);
        return trackDtoMapper.apply(t);
    }

    @Override
    public TrackDto getById(Long aLong) {
        Optional<Track> optionalTrack = trackRepository.findById(aLong);

        if (optionalTrack.isEmpty()) {
            throw new NoSuchElementException("No se encontró ese track");
        }

        Track t = optionalTrack.get();
        return trackDtoMapper.apply(t);
    }

    @Override
    public List<TrackDto> getAll() {
        List<Track> tracks = trackRepository.findAll();

        return tracks
                .stream()
                .map(trackDtoMapper)
                .toList();
    }

    @Override
    public List<ConsignaUnoDto> getAllFiltrado(Long idArtist, Long idGenre) {

        //Primero verifico q no me hayan pasado por parametro id invalido ya se de artista o de genero.

        Optional<Artist> optionalArtist = artistRepository.findById(idArtist);

        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("El id de artista no es valido");
        }
        Optional<Genre> optionalGenre = genreRepository.findById(idGenre);
        if (optionalGenre.isEmpty()) {
            throw new NoSuchElementException("El id de genero no es valido");
        }
        //Obtengo todos los tracks de la BD
        List<Track> tracks = trackRepository.findAll();

        // Filtro para obtener solamente los tracks de ese  artista pasado por parametro
        tracks = tracks.stream()
                .filter(t -> Objects.equals(t.getAlbum().getArtist().getId(), idArtist))
                .toList();

        if (tracks.isEmpty()) {
            throw new NullPointerException();
        }

        // Filtrar para obtener los tracks solamente de ese genero pasado por parametro
        if (idGenre != null) {
            tracks = tracks.stream()
                    .filter(t -> t.getGenre().getId() == idGenre)
                    .toList();
        }

        //retorno los tracks
        return tracks
                .stream()
                .map(consginaUnoDtoMapper)
                .toList();
    }

    @Override
    public List<ConsignaTresDto> getAllTracksByCustomerId(Long customerId) {
        //Verifico que ese cliente exista en la BD
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new NoSuchElementException("Cliente no encontrado");
        }
        // Obtengo todas las facturas de ese cliente.
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        // Si el cliente no tiene facturas, significa que no tiene tracks
        if (invoices.isEmpty()) {
            throw new NoSuchElementException("El cliente no tiene facturas y, por lo tanto, no posee tracks");
        }
        //creo una lista vacia de tracks
        List<Track> tracks = new ArrayList<>();
       /* recorro las facturas del cliente pasado por parametro y en cada iteracion:
        1. obtengo todos los items de  de cada factura
        2. itero por los items y aprovecho que gracias a hibernate, cada item de factura
        tiene su campo track,entonces agrego cada track a la lista de tracks creada mas arriba.

        */

        for (Invoice invoice : invoices) {
            List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getId());
            for (InvoiceItem item : invoiceItems) {
                tracks.add(item.getTrack());
            }
        }
        // Si el cliente tiene facturas pero no hay tracks asociados
        if (tracks.isEmpty()) {
            throw new NoSuchElementException("El cliente tiene facturas, pero no hay tracks asociados a estas facturas");
        }

        /* ya tengo todos los tracks del requerimiento, solo me falta aplicar distintc para no repetir
        y map para convertirlo al DTO pedido por el analista
         */

        return tracks.stream()
                .distinct()
                .map(consignaTresDtoMapper)
                .toList();
    }

    @Override
    public List<ConsignaCuatroDto> getTracksByCustomerIdAndGenre(Long customerId, Long genreId) {
        // me fijo que el customer pasado por parametro, exista en la BD
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new NoSuchElementException("Cliente no encontrado");
        }

        //idem con genero
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isEmpty()) {
            throw new NoSuchElementException("El id de genero no es valido");
        }
        //obtengo todos las facturas de ese cliente.
        List<Invoice> customerInvoices = invoiceRepository.findByCustomerId(customerId);
        if (customerInvoices.isEmpty()) {
            throw new NoSuchElementException("El cliente no tiene facturas y, por lo tanto, no posee tracks");
        }

        //creo una lista de tracks vacia llamada filteredTracks
        List<Track> filteredTracks = new ArrayList<>();
        /* recorro todas las facturas de ese cliente y en cada iteracion:
        1. itero por cada item factura de cada factura de ese cliente.
        1.1 obtengo gracias a hibernate el track de ese item factura
        1.2 si el track pertenece al genero pasado por "parametro"
        , entonces lo agrego a la lista filteredTracks.

         */
        for (Invoice invoice : customerInvoices) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                Track track = item.getTrack();
                if (track.getGenre().getId().equals(genreId)) {
                    filteredTracks.add(track);
                }
            }
        }

        // Eliminar duplicados
        filteredTracks = filteredTracks.stream().distinct().toList();

        if (filteredTracks.isEmpty()) {
            throw new NoSuchElementException("No se encontraron tracks para el cliente y género especificados");
        }

        //lo devuelvo previo mapeo para que la response cumpla los requerimientos del cliente.
        return filteredTracks.stream()
                .map(consignaCuatroDtoMapper)
                .toList();
    }


    @Override
    public List<ConsignaCincoDto> getTracksByCustomerIdAndArtist(Long customerId, Long artistId) {
        //Verifico que el cliente pasado por parametro exista en la BD
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new NoSuchElementException("Cliente no encontrado");
        }
         //Misma verificacion para el artista.

        Optional<Artist> optionalArtist = artistRepository.findById(artistId);

        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("El id de artista no es valido");
        }
        //Obtengo las facturas de ese customer pasado por parametro.
        List<Invoice> customerInvoices = invoiceRepository.findByCustomerId(customerId);
        if (customerInvoices.isEmpty()) {
            throw new NoSuchElementException("El cliente no tiene facturas y, por lo tanto, no posee tracks");
        }
        //creo una lista vacia de tracks llamada filteredTracks
        List<Track> filteredTracks = new ArrayList<>();
        /* recorro todas las facturas de ese cliente y en cada iteracion:
        1. itero por cada item factura de cada factura de ese cliente.
        1.1 obtengo gracias a hibernate el track de ese item factura
        1.2 si el track pertenece al artista pasado por "parametro"
        , entonces lo agrego a la lista filteredTracks.
        observer lo potente que es hibernate para hacer esto.

         */
        for (Invoice invoice : customerInvoices) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                Track track = item.getTrack();
                if (track.getAlbum().getArtist().getId().equals(artistId)) {
                    filteredTracks.add(track);
                }
            }
        }

        // Eliminar duplicados
        filteredTracks = filteredTracks.stream().distinct().toList();

        if (filteredTracks.isEmpty()) {
            throw new NoSuchElementException("No se encontraron tracks para el cliente y artista especificados");
        }

        //lo devuelvo previo mapeo para que la response cumpla los requerimientos del cliente.

        return filteredTracks.stream()
                .map(consignaCincoDtoMapper)
                .toList();
    }
}




