package org.quantum.rms.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private List<Route> routes;

}
