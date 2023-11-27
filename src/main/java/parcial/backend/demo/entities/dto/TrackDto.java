package parcial.backend.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDto {
    private long id;
    private String name;
    private AlbumDto album;
    private MediaTypeDto mediaType;
    private GenreDto genre;
    private String composer;
    private long milliseconds;
    private long bytes;
    private double unitPrice;
}
