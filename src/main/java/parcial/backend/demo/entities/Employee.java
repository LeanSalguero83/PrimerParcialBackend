package parcial.backend.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeId")
    private long id;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "FirstName")
    private String firstName;
    private String title;
    @ManyToOne
    @JoinColumn(name = "ReportsTo")
    private Employee reportTo;
    @Column(name = "BirthDate")
    private LocalDateTime birthDate;
    @Column(name = "HireDate")
    private LocalDateTime hireDate;
    private String address;
    private String city;
    private String state;
    private String country;
    @Column(name = "PostalCode")
    private String postalCode;
    private String phone;
    private String fax;
    private String email;
}
