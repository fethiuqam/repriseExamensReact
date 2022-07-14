package ca.uqam.repriseexamen.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("etudiant")
public class Etudiant extends Utilisateur {
    public static final String TYPE_NAME = "etudiant";
    private String codePermanent;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @OneToMany(mappedBy = "etudiant")
    private List<DemandeRepriseExamen> demandes;

    public Etudiant(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles, String codePermanent, String telephone) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.codePermanent = codePermanent;
        this.telephone = telephone;
    }
}
