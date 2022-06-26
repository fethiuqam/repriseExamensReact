package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {


    @Id
    @GeneratedValue
    private Long id;

    private String nom;

    @ElementCollection(targetClass = Permission.class)
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
}
