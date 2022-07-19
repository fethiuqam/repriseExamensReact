package ca.uqam.repriseexamen.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private String nom;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    private List<Permission> permissions;

    public Role(String nom, List<Permission> permissions) {
        this.nom = nom;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<Utilisateur> utilisateurs;
}
