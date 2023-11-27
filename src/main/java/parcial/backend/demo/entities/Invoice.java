package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceId")
    private long id;
    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;
    @Column(name = "InvoiceDate")
    private LocalDateTime invoiceDate;
    @Column(name = "BillingAddress")
    private String billingAddress;
    @Column(name = "BillingCity")
    private String billingCity;
    @Column(name = "BillingCountry")
    private String billingCountry;
    @Column(name = "BillingPostalCode")
    private String billingPostalCode;
    private double total;
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceItem> invoiceItems;
}
