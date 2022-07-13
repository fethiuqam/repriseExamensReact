package ca.uqam.repriseexamen.model;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@Inheritance
public abstract class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String nom;
    protected String prenom;
    protected String email;
    @Column(unique=true)
    protected String codeMs;
    protected String motDePasse;
    @Column(name = "dtype", insertable = false, updatable = false)
    protected String type;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateurs_roles",
            joinColumns = @JoinColumn(name = "utilisateurs_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    protected Collection<Role> roles;

    public Utilisateur(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.codeMs = codeMs;
        this.motDePasse = motDePasse;
        this.roles = roles;
    }

}
