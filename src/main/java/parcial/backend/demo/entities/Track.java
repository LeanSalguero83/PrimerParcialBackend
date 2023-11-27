package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album album;
    @ManyToOne
    @JoinColumn(name = "mediatypeid")
    private MediaType mediaType;
    @ManyToOne
    @JoinColumn(name = "genreid")
    private Genre genre;
    private String composer;
    private long milliseconds;
    private long bytes;
    @Column(name = "UnitPrice")
    private double unitPrice;
    @ManyToMany(mappedBy = "tracks")
    private List<Playlist> playlists;
}
