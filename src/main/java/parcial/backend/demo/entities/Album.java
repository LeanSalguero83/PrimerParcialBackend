package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "albums")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Albumid")
    private long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artistid")
    private Artist artist;
    @OneToMany(mappedBy = "album")
    List<Track> tracks;
}
