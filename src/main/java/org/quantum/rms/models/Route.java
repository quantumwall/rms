package org.quantum.rms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "documents")
@Entity
@Table(name = "route")
public class Route {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipment_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "Указанная дата не может быть позднее сегодняшней")
    private LocalDate shipmentDate;

    @Column(name = "departure_city")
    @NotBlank(message = "Не указан город загрузки")
    @Size(min = 2, max = 100, message = "Название города должно быть в промежутке 2 - 100 символов")
    private String departureCity;

    @Column(name = "destination_city")
    @NotBlank(message = "Не указан город разгрузки")
    @Size(min = 2, max = 100, message = "Название города должно быть в промежутке 2 - 100 символов")
    private String destinationCity;

    @Column(name = "bill_number")
    @Max(value = Integer.MAX_VALUE, message = "Номер счета/акта превышает максимально допустимое значение 2147483647")
    @Positive(message = "Номер счета/акта должно быть положительным числом")
    private Integer billNumber;

    @Column(name = "price")
    @Positive(message = "Стоимость рейса должна быть положительным числом")
    private BigDecimal price;

    @Column(name = "is_paid")
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToOne(mappedBy = "route", cascade = CascadeType.PERSIST)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @OneToMany(mappedBy = "route")
    private List<Document> documents;
}
