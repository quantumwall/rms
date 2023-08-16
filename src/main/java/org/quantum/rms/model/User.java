package org.quantum.rms.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{constraint.user.name.empty}")
    private String name;

    @Column(unique = true)
    private String email;

    @NotNull(message = "{constraint.user.password.empty}")
    @Size(min = 2, message = "{constraint.user.password.size}")
    private String password;
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Route> routes = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_driver", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "driver_id") })
    private Set<Driver> drivers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_customer", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "customer_id") })
    private Set<Customer> customers = new HashSet<>();
    
    public void addDriver(Driver driver) {
	drivers.add(driver);
    }
    
    public void deleteDriver(Driver driver) {
	drivers.remove(driver);
    }
    
    public void addCustomer(Customer customer) {
	customers.add(customer);
    }
    
    public void deleteCustomer(Customer customer) {
	customers.remove(customer);
    }

}
