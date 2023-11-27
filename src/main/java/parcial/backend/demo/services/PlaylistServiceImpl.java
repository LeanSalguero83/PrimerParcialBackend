package parcial.backend.demo.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parcial.backend.demo.entities.*;
import parcial.backend.demo.entities.dto.*;
import parcial.backend.demo.repositories.*;
import parcial.backend.demo.services.mappers.ConsignaDosDtoMapper;
import parcial.backend.demo.services.mappers.NuevaPlaylistResponseDtoMapper;
import parcial.backend.demo.services.mappers.NuevaPlaylistResponseDtoRECUPERATORIOMapper;
import parcial.backend.demo.services.mappers.PlaylistDtoMapper;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    PlaylistRepository playlistRepository;
    ArtistRepository artistRepository;
    GenreRepository genreRepository;
    TrackRepository trackRepository;
    PlaylistDtoMapper playlistDtoMapper;
    ConsignaDosDtoMapper consignaDosDtoMapper;
    CustomerRepository customerRepository;
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    NuevaPlaylistResponseDtoMapper nuevaPlaylistResponseDtoMapper;
    NuevaPlaylistResponseDtoRECUPERATORIOMapper nuevaPlaylistResponseDtoRECUPERATORIOMapper;

    @Override
    public PlaylistDto add(PlaylistDto entity) {
        Playlist p = new Playlist();
        p.setName(entity.getName());
        Playlist nuevo = playlistRepository.save(p);
        return playlistDtoMapper.apply(nuevo);
    }

    @Override
    public PlaylistDto update(Long id, PlaylistDto entity) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(id);

        if (optionalPlaylist.isEmpty()) {
            throw new NoSuchElementException("No se encontró esa playlist");
        }

        Playlist p = optionalPlaylist.get();
        p.setName(entity.getName());
        playlistRepository.save(p);

        return playlistDtoMapper.apply(p);
    }

    @Override
    public PlaylistDto delete(Long aLong) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(aLong);

        if (optionalPlaylist.isEmpty()) {
            throw new NoSuchElementException("No se encontró esa playlist");
        }

        Playlist p = optionalPlaylist.get();
        playlistRepository.delete(p);
        return playlistDtoMapper.apply(p);
    }

    @Override
    public PlaylistDto getById(Long aLong) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findById(aLong);

        if (optionalPlaylist.isEmpty()) {
            throw new NoSuchElementException("No se encontró esa playlist");
        }

        Playlist p = optionalPlaylist.get();
        return playlistDtoMapper.apply(p);
    }

    @Override
    public List<PlaylistDto> getAll() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlists
                .stream()
                .map(playlistDtoMapper)
                .toList();
    }

    @Override
    public ConsignaDosDto crear(ConsginaDosDto2 entity) {

        //Verifico que el idArtist mandado en "body" se refiera a un artista que exista
        Optional<Artist> optionalArtist = artistRepository.findById(entity.getIdArtist());

        if (optionalArtist.isEmpty()) {
            throw new NoSuchElementException("No se encontró el artista");
        }
        //Verifico que el idGenre mandado en "body" se refiera a un genero que exista

        Optional<Genre> optionalGenre = genreRepository.findById(entity.getIdGenre());
        if (optionalGenre.isEmpty()) {
            throw new NoSuchElementException("No se encontró el género");
        }

        Genre genre = optionalGenre.get();

        Artist artist = optionalArtist.get();

        //Obtengo todos los tracks y uso streams para filtrar solo
        // los que sean del genero y artista pasado por parametro.
        List<Track> tracks = trackRepository.findAll();
        tracks = tracks.stream()
                .filter(track -> track.getAlbum().getArtist() == artist && track.getGenre() == genre)
                .collect(Collectors.toList());

        //Hago que los tracks esten ordenados de manera aleatoria
        Collections.shuffle(tracks);


        //Creo una lista de Tracks que van a ser los que se van a agregar a la nueva playlist.
        List<Track> tracksParaPlaylist = new ArrayList<>();

        //recorro los tracks filtrados por genre y artist
        // y en cada iteracion obtengo los milisegundos de cada track y se los agrego al acumulador
        // si ese acumulador es menor a los milisegundos pasados por parametro en el "body"
        // entonces agrego el track a la lista
        long acumulador = 0;
        for (Track t : tracks) {
            acumulador += t.getMilliseconds();
            if (acumulador <= (entity.getMinutos() * 60 * 1000)) {
                tracksParaPlaylist.add(t);
            }
        }

        //Creo la nueva playList y le empiezo a settear los atributos y la guardo en la base de datos.
        Playlist nueva = new Playlist();
        nueva.setTracks(tracksParaPlaylist);
        nueva.setName(entity.getName()); //este nombre lo recibi por el "body"
        Playlist p = playlistRepository.save(nueva);

        // si la lista de tracks, esta vacia, tiro una excepcion.
        if (tracksParaPlaylist.isEmpty()) {
            throw new NullPointerException();
        }
        // Ordenar la lista de tracks de la playlist por precio unitario de mayor a menor para la respuesta
        List<Track> tracksOrdenadosPorPrecio = tracksParaPlaylist.stream()
                .sorted(Comparator.comparing(Track::getUnitPrice).reversed())
                .collect(Collectors.toList());
        nueva.setTracks(tracksOrdenadosPorPrecio); // le setteo la lista de tracks ordenada para la response

        //retorno la lista pero de acuerdo a como quiere el responseDto el analista funcional.
        return consignaDosDtoMapper.apply(p);
    }

    @Override
    public NuevaPlaylistResponseDto crear2(NuevaPlaylistBodyDto nuevaPlaylistBodyDto) {
        //Verifico que el idCliente mandado en "body" se refiera a un customer que exista

        Optional<Customer> customerOpt = customerRepository.findById(nuevaPlaylistBodyDto.getIdCliente());
        if (customerOpt.isEmpty()) {
            throw new NoSuchElementException("Cliente no encontrado");
        }

        // Obteniendo las facturas de ese customer
        List<Invoice> invoices = invoiceRepository.findByCustomerId(nuevaPlaylistBodyDto.getIdCliente());
        if (invoices.isEmpty()) {
            throw new NoSuchElementException("El cliente no tiene facturas y, por lo tanto, no posee tracks");
        }

        /* Obteniendo los tracks de las facturas( a traves de los invoice items de esas facturas)
         1-creo una lista vacia de tracks
         2-recorro las facturas del cliente recibido en el "body"
         3-en cada iteracion busco todos los items de cada factura
         4-itero sobre esos items
         5-de cada item obtengo su track(gracias a hibernate)
         6- si cumple con el criterio de filtro del composer, lo agrego a la lista de tracks creada en 1- */
        List<Track> customerTracks = new ArrayList<>();
        for (Invoice invoice : invoices) {
            List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getId());
            for (InvoiceItem item : invoiceItems) {
                Track track = item.getTrack();
                // Agregar el track si cumple con el filtro de compositor
                if (track.getComposer() != null && track.getComposer().contains(nuevaPlaylistBodyDto.getComposerFilter())) {
                    customerTracks.add(track);
                }
            }
        }

        // Eliminar duplicados de la lista de tracks y ordenar aleatoriamente
        customerTracks = customerTracks.stream().distinct().collect(Collectors.toList());
        Collections.shuffle(customerTracks);

        /* Hasta este punto tengo una lista de tracks que:
        1. Fueron comprados por el ciente pasado en el "body".
        2. Estan ordenados aleatoriamente
        3. Cumplen el filtro del composer "pasado en el body"
         */

       /*Ahora debo ver cuantos de esos tracks entran en una playlist nueva q debe tener la duracion
       maxima pasada via "body" en  NuevaPlaylistBodyDto por parametro */

        List<Track> selectedTracks = new ArrayList<>();
        long totalDuration = 0;
        /*Recorremos los tracks y en cada iteracion:
        1-Obtenemos los milisegundos de cada track y lo asignamos a la variable trackDuration
        2- si la totalDuration+ la duracion de ese track es menor a la duracion maxima pasada en el
        "body":
        2.1- agrego ese track a los selectedTracks
        2.2-incremento la totalDuration en trackDuration.
        sino salgo  del bucle.
         */
        for (Track track : customerTracks) {
            long trackDuration = track.getMilliseconds();
            if (totalDuration + trackDuration <= nuevaPlaylistBodyDto.getDuracionMaxima()) {
                selectedTracks.add(track);
                totalDuration += trackDuration;
            } else {
                break;
            }
        }
        /* creo una nueva nuevaPlaylist y empiezo a setterle los atributos:
        1. El name fue pasado via "body" en nuevaPlaylistBodyDto
        2. la lista de tracks es la lista de selectedTracks ya totalmente pulida.
        3. El id no hace falta seterle porque tenemos IDENTITY en hibernate para esa entidad
        4. Finalmente la guardamos en la base de datos
         */

        Playlist nuevaPlaylist = new Playlist();
        nuevaPlaylist.setName(nuevaPlaylistBodyDto.getNombrePlaylist());
        nuevaPlaylist.setTracks(selectedTracks);
        Playlist savedPlaylist = playlistRepository.save(nuevaPlaylist);
        if (selectedTracks.isEmpty()) {
            throw new NullPointerException();
        }

        // Ordenar la lista de tracks de la playlist por precio unitario de mayor a menor para la respuesta
        List<Track> tracksOrdenadosPorPrecio = selectedTracks.stream()
                .sorted(Comparator.comparing(Track::getUnitPrice).reversed())
                .collect(Collectors.toList());
        savedPlaylist.setTracks(tracksOrdenadosPorPrecio); // le setteo la lista de tracks ordenada para la response


       //retorno la nueva playList utilizando el mappeador para que quede como pide el requerimiento.
        return nuevaPlaylistResponseDtoMapper.apply(savedPlaylist);
    }

    @Override
    public NuevaPlaylistResponseDto crear3(NuevaPlaylistBodyDtoJUEVES dtoJUEVES) {
        // Verificamos si el cliente existe
        Optional<Customer> customerOpt = customerRepository.findById(dtoJUEVES.getIdCliente());
        if (customerOpt.isEmpty()) {
            throw new NoSuchElementException("Cliente no encontrado");
        }

        // Verificamos si el género existe
        Optional<Genre> genreOpt = genreRepository.findById(dtoJUEVES.getIdGenre());
        if (genreOpt.isEmpty()) {
            throw new NoSuchElementException("Género no encontrado");
        }

        // Obteniendo las facturas de ese customer
        List<Invoice> invoices = invoiceRepository.findByCustomerId(dtoJUEVES.getIdCliente());
        if (invoices.isEmpty()) {
            throw new NoSuchElementException("El cliente no tiene facturas y, por lo tanto, no posee tracks");
        }

        /* Obteniendo los tracks de las facturas( a traves de los invoice items de esas facturas)
         1-creo una lista vacia de tracks
         2-recorro las facturas del cliente recibido en el "body"
         3-en cada iteracion busco todos los items de cada factura
         4-itero sobre esos items
         5-de cada item obtengo su track(gracias a hibernate)
         6- si cumple con el criterio de filtro del composer y del genero, lo agrego a la lista de tracks creada en 1-
             */
        List<Track> customerTracks = new ArrayList<>();
        for (Invoice invoice : invoices) {
            List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getId());
            for (InvoiceItem item : invoiceItems) {
                Track track = item.getTrack();
                // Agregar el track si cumple con el filtro de compositor y el género
                if (track.getComposer() != null && track.getComposer().contains(dtoJUEVES.getComposerFilter())
                        && track.getGenre() != null && Objects.equals(track.getGenre().getId(), dtoJUEVES.getIdGenre())) {
                    customerTracks.add(track);
                }
            }
        }

        // Eliminar duplicados de la lista de tracks y ordenar aleatoriamente
        customerTracks = customerTracks.stream().distinct().collect(Collectors.toList());
        Collections.shuffle(customerTracks);
         /* Hasta este punto tengo una lista de tracks que:
        1. Fueron comprados por el ciente pasado en el "body".
        2. Estan ordenados aleatoriamente
        3. Cumplen el filtro del composer "pasado en el body"
        4. Cumplen con el filtro de ser del genero pasado en el "body".
         */

       /*Ahora debo ver cuantos de esos tracks entran en una playlist nueva q debe tener la duracion
       maxima pasada via "body" en  NuevaPlaylistBodyDtoJUEVES por parametro */

        List<Track> selectedTracks = new ArrayList<>();
        long totalDuration = 0;
        /*Recorremos los tracks y en cada iteracion:
        1-Obtenemos los milisegundos de cada track y lo asignamos a la variable trackDuration
        2- si la totalDuration + la duracion de ese track es menor a la duracion maxima pasada en el
        "body":
        2.1- agrego ese track a los selectedTracks
        2.2-incremento la totalDuration en trackDuration.
        sino salgo  del bucle.
         */
        for (Track track : customerTracks) {
            long trackDuration = track.getMilliseconds();
            if (totalDuration + trackDuration <= dtoJUEVES.getDuracionMaxima()) {
                selectedTracks.add(track);
                totalDuration += trackDuration;
                // Salir del bucle si todos los tracks disponibles han sido considerados
                if (selectedTracks.size() == customerTracks.size()) {
                    break;
                }
            } else {
                break;
            }
        }
        /* creo una nueva nuevaPlaylist y empiezo a setterle los atributos:
        1. El name fue pasado via "body" en nuevaPlaylistBodyDto
        2. la lista de tracks es la lista de selectedTracks ya totalmente pulida.
        3. El id no hace falta seterle porque tenemos IDENTITY en hibernate para esa entidad
        4. Finalmente la guardamos en la base de datos
         */

        Playlist nuevaPlaylist = new Playlist();
        nuevaPlaylist.setName(dtoJUEVES.getNombrePlaylist());
        nuevaPlaylist.setTracks(selectedTracks);
        Playlist savedPlaylist = playlistRepository.save(nuevaPlaylist);
        if (selectedTracks.isEmpty()) {
            throw new NullPointerException();
        }

        // Ordenar la lista de tracks de la playlist por precio unitario de mayor a menor para la respuesta
        List<Track> tracksOrdenadosPorPrecio = selectedTracks.stream()
                .sorted(Comparator.comparing(Track::getUnitPrice).reversed())
                .collect(Collectors.toList());
        savedPlaylist.setTracks(tracksOrdenadosPorPrecio); // le setteo la lista de tracks ordenada para la response


        //retorno la nueva playList utilizando el mappeador para que quede como pide el requerimiento.
        return nuevaPlaylistResponseDtoMapper.apply(savedPlaylist);

    }

    @Override
    public NuevaPlaylistResponseDtoRECUPERATORIO crear4(NuevaPlayListBodyDtoRecuperatorio dtoRECUPERATORIO) {
        // Verificación de existencia del cliente y del artista
        Customer cliente = customerRepository.findById(dtoRECUPERATORIO.getIdCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
        Artist artista = artistRepository.findById(dtoRECUPERATORIO.getIdArtist())
                .orElseThrow(() -> new NoSuchElementException("Artista no encontrado"));

        // Obtener las facturas del cliente y filtrar los tracks por el artista
        List<Invoice> facturasCliente = invoiceRepository.findByCustomerId(cliente.getId());
        List<Track> tracksComprados = new ArrayList<>();
        for (Invoice factura : facturasCliente) {
            for (InvoiceItem item : factura.getInvoiceItems()) {
                Track track = item.getTrack();
                if (track.getAlbum().getArtist().equals(artista)) {
                    tracksComprados.add(track);
                }
            }
        }

        // Agrupar los tracks por álbum
        Map<Long, List<Track>> tracksPorAlbum = tracksComprados.stream()
                .collect(Collectors.groupingBy(track -> track.getAlbum().getId()));

        // Agregar tracks a la playlist por álbum
        List<Track> tracksParaPlaylist = new ArrayList<>();
        long duracionAcumulada = 0;
        for (List<Track> tracksDelAlbum : tracksPorAlbum.values()) {
            for (Track track : tracksDelAlbum) {
                long duracionTrack = track.getMilliseconds();
                if (duracionAcumulada + duracionTrack <= dtoRECUPERATORIO.getDuracionMaxima()) {
                    tracksParaPlaylist.add(track);
                    duracionAcumulada += duracionTrack;
                } else {
                    break; // Salir del bucle si alcanzamos la duración máxima
                }
            }
        }

        // Crear y guardar la nueva playlist
        Playlist nuevaPlaylist = new Playlist();
        nuevaPlaylist.setName(dtoRECUPERATORIO.getNombrePlaylist());
        nuevaPlaylist.setTracks(tracksParaPlaylist);
        playlistRepository.save(nuevaPlaylist);

        // Crear y devolver el DTO de respuesta
        return nuevaPlaylistResponseDtoRECUPERATORIOMapper.apply(nuevaPlaylist);
    }




}
