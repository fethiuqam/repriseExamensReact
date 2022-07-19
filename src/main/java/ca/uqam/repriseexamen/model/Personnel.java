package ca.uqam.repriseexamen.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("personnel")
public class Personnel extends Utilisateur {
    private String matricule;

    public Personnel(String nom, String prenom, String codeMs, String motDePasse, Set<Role> roles, String matricule) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.matricule = matricule;
    }

    @Builder
    public Personnel(Long id, String nom, String prenom, String email, String codeMs, String motDePasse, String type,
            Set<Role> roles, String matricule) {
        super(id, codeMs, motDePasse, type, roles, nom, prenom, email);
        this.matricule = matricule;
    }
}
