package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Inheritance
@AllArgsConstructor
public abstract class Utilisateur {

    // Attributs

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(unique = true)
    protected String codeMs;

    @JsonIgnore
    protected String motDePasse;

    @Column(name = "dtype", insertable = false, updatable = false)
    protected String type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateurs_roles", joinColumns = @JoinColumn(name = "utilisateurs_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    protected Set<Role> roles;

    protected String nom;
    protected String prenom;
    protected String email;

    // Méthodes publiques

    public Utilisateur(String nom, String prenom, String codeMs, String motDePasse, Set<Role> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.codeMs = codeMs;
        this.motDePasse = motDePasse;
        this.roles = roles;
    }

    public Set<Permission> getPermissions() {
        return roles.stream().flatMap(role -> role.getPermissions().stream()).collect(Collectors.toSet());
    }

}
