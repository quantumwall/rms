package org.quantum.rms.models;

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
import jakarta.validation.constraints.Max;
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
    @PastOrPresent(message = "Указанная дата не может быть позднее сегодняшней")
    private LocalDate shipmentDate;

    @NotBlank(message = "Не указан город загрузки")
    @Size(min = 2, max = 100, message = "Название города должно быть в промежутке 2 - 100 символов")
    private String departureCity;

    @NotBlank(message = "Не указан город разгрузки")
    @Size(min = 2, max = 100, message = "Название города должно быть в промежутке 2 - 100 символов")
    private String destinationCity;

    @Max(value = Integer.MAX_VALUE, message = "Номер счета/акта превышает максимально допустимое значение 2147483647")
    @Positive(message = "Номер счета/акта должно быть положительным числом")
    private Integer billNumber;

    @Positive(message = "Стоимость рейса должна быть положительным числом")
    private BigDecimal price;

    @Column(name = "is_paid")
    private boolean paid;

    @ManyToOne
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Cargo cargo;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private User user;

//    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
//    private List<Document> documents;
}
