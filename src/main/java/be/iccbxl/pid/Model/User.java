package be.iccbxl.pid.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotBlank(message = "Le login ne peut être vide")
    private String login;

    @NotBlank(message = "Le mot de passe ne peut être vide")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).*$", message = "Le mot de passe doit contenir un caractère spécial et une majuscule minimum")
    private String password;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    @NotBlank(message = "L'email ne peut être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    private String langue;
    private LocalDateTime created_at;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude // Exclure la collection roles de la méthode toString pour éviter la récursion
    private List<Role> roles = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude // Exclure la collection representations de la méthode toString pour éviter la récursion
    private List<Representation> representations = new ArrayList<>();

    public User(String login, String password, String firstname, String lastname, String email, String langue) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.langue = langue;
        this.created_at = LocalDateTime.now();
        this.roles = new ArrayList<>();
    }

    public User addRole(Role role) {
        if(!this.roles.contains(role)) {
            this.roles.add(role);
            role.getUsers().add(this);
        }

        return this;
    }

    public User removeRole(Role role) {
        if(this.roles.contains(role)) {
            this.roles.remove(role);
            role.getUsers().remove(this);
        }

        return this;
    }

    public User addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.addUser(this);
        }

        return this;
    }

    public User removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            representation.getUsers().remove(this);
        }

        return this;
    }

    public static User createInstance() {
        return new User();
    }
}
