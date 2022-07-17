package ca.uqam.repriseexamen.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("personnel")
public class Personnel extends Utilisateur {
    private int employeId;

    public Personnel(String nom, String prenom, String codeMs, String motDePasse, List<Role> roles, int employeId) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.employeId = employeId;
    }

    @Builder
    public Personnel(Long id, String nom, String prenom, String email, String codeMs, String motDePasse, String type, Collection<Role> roles, int employeId) {
        super(id, codeMs, motDePasse, type, roles, nom, prenom, email);
        this.employeId = employeId;
    }
}
