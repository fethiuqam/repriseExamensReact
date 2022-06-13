package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private @Id @GeneratedValue Long id;

    private String nom;

    @ElementCollection(targetClass = Permission.class)
    private List<Permission> permissions;

    public Role(String nom, List<Permission> permissions) {
        this.nom = nom;
        this.permissions = permissions;
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
}
