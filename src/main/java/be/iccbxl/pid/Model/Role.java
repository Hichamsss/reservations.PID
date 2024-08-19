package be.iccbxl.pid.Model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String role;
    
    @ManyToMany
    @JoinTable(
            name = "user_role", 
            joinColumns = @JoinColumn(name = "role_id"), 
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude // Exclure la collection users de la méthode toString pour éviter la récursion
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public Role addUser(User user) {
        if(!this.users.contains(user)) {
            this.users.add(user);
            user.addRole(this);
        }
        
        return this;
    }
    
    public Role removeUser(User user) {
        if(this.users.contains(user)) {
            this.users.remove(user);
            user.getRoles().remove(this);
        }
        
        return this;
    }
}
