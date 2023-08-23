package org.quantum.rms.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString(exclude = "documents")
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "billNumber", "user_id" }) })
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "{constraint.route.shipment_date.past_or_present}")
    private LocalDate shipmentDate;

    @NotBlank(message = "{constraint.route.city.not_blank}")
    @Size(min = 2, max = 100, message = "{constraint.route.city.size}")
    private String departureCity;

    @NotBlank(message = "{constraint.route.city.not_blank}")
    @Size(min = 2, max = 100, message = "{constraint.route.city.size}")
    private String destinationCity;

    private String billNumber;

    @Positive(message = "{constraint.route.price.positive}")
    private BigDecimal price;

    @Column(name = "is_paid")
    private boolean paid;

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Cargo cargo = new Cargo();;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
	return "Route [id=" + id + ", shipmentDate=" + shipmentDate + ", departureCity=" + departureCity
		+ ", destinationCity=" + destinationCity + ", billNumber=" + billNumber + ", price=" + price + ", paid="
		+ paid + "]";
    }

//    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
//    private List<Document> documents;
}
