package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @Column(name = "GenreId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
