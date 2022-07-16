package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Inheritance
@AllArgsConstructor
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String nom;
    protected String prenom;
    protected String email;
    @Column(unique = true)
    protected String codeMs;
    protected String motDePasse;
    @Column(name = "dtype", insertable = false, updatable = false)
    protected String type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateurs_roles", joinColumns = @JoinColumn(name = "utilisateurs_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    protected Collection<Role> roles;

    public Utilisateur(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.codeMs = codeMs;
        this.motDePasse = motDePasse;
        this.roles = roles;
    }

    public Set<Permission> getPermissions() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());
    }

}
