package ca.uqam.repriseexamen.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("personnel")
public class Personnel extends Utilisateur {
    private int employeId;

    public Personnel(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles, int employeId) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.employeId = employeId;
    }
}
