package ca.uqam.repriseexamen.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("enseignant")
public class Enseignant extends Utilisateur {
    private String matricule;
    @OneToMany(mappedBy = "enseignant")
    @ToString.Exclude
    private List<CoursGroupe> coursGroupeListe;

    public Enseignant(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles, String matricule) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.matricule = matricule;
    }
}