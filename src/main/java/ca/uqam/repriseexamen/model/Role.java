package ca.uqam.repriseexamen.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String nom;

    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    private List<Permission> permissions;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<Utilisateur> utilisateurs;
}
