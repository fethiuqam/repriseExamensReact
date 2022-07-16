package ca.uqam.repriseexamen.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@DiscriminatorValue("etudiant")
public class Etudiant extends Utilisateur {

    // Attributs

    @JsonManagedReference(value = "demandesEtudiant")
    @OneToMany(mappedBy = "etudiant")
    private List<DemandeRepriseExamen> demandes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coursGroupe_etudiant",
            joinColumns = @JoinColumn(name = "etudiant_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_groupe_id"))
    private List<CoursGroupe> coursGroupes;

    public static final String TYPE_NAME = "etudiant";
    private String codePermanent;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    public Etudiant(String nom, String prenom, String codeMs, String motDePasse, Collection<Role> roles, String codePermanent, String telephone) {
        super(nom, prenom, codeMs, motDePasse, roles);
        this.codePermanent = codePermanent;
        this.telephone = telephone;
    }
}
