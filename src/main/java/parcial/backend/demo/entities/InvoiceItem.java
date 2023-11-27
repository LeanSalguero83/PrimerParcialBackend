package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "invoice_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceLineId")
    private long id;
    @ManyToOne
    @JoinColumn(name = "InvoiceId")
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "TrackId")
    private Track track;
    @Column(name = "UnitPrice")
    private double unitPrice;
    private Integer quantity;
}
