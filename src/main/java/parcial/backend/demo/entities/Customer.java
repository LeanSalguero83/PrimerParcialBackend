package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private long id;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Company")
    private String company;
    @Column(name = "Address")
    private String address;
    @Column(name = "City")
    private String city;
    @Column(name = "State")
    private String state;
    @Column(name = "Country")
    private String country;
    @Column(name = "PostalCode")
    private String postalCode;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Fax")
    private String fax;
    @Column(name = "Email")
    private String email;
    @ManyToOne
    @JoinColumn(name = "SupportRepId")
    private Employee supportRep;
}
