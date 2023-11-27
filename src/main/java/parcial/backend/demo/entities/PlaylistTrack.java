package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "playlist_track")
@IdClass(PlaylistTrackId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrack {
    @Id
    @ManyToOne
    @JoinColumn(name = "PlaylistId")
    private Playlist playlist;
    @Id
    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track track;
}
