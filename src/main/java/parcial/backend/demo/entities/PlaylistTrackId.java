package parcial.backend.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistTrackId implements Serializable {
    private Track track;
    private Playlist playlist;
}
