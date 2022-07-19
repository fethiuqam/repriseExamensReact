package ca.uqam.repriseexamen.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("enseignant")
public class Enseignant extends Utilisateur {

    // Attributs

    private String matricule;

    @JsonIgnore
    @OneToMany(mappedBy = "enseignant")
    @ToString.Exclude
    private List<CoursGroupe> coursGroupeListe;

    // Méthodes publiques

    public Enseignant(String nom, String prenom, String codeMs, String motDePasse, Set<Role> roles, String matricule) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.matricule = matricule;
    }
}
