package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DemandeRepriseExamen {

    // Attributs

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.PERSIST)
    private List<Statut> listeStatut;

    @ManyToOne
    private Etudiant etudiant;

    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.PERSIST)
    private List<Justification> listeJustification;

    @ManyToOne
    private CoursGroupe coursGroupe;

    private LocalDate absenceDateDebut;
    private LocalDate absenceDateFin;
    private LocalDate dateSoumission;
    private MotifAbsence motifAbsence;
    private String absenceDetails;
    private String descriptionExamen;

    // Méthodes publiques

    public LocalDateTime getDateHeureSoumission(){
        LocalDateTime heureSoumission = null;

        if(listeStatut != null){
            Optional<Statut> statutSoumission = listeStatut.stream()
                    .filter(o -> o.getTypeStatut().equals(TypeStatut.SOUMISE))
                    .findFirst();

            heureSoumission = statutSoumission.map(Statut::getDateHeure).orElse(null);
        }
        return heureSoumission;
    }

    public TypeStatut getStatutCourant(){
        TypeStatut typeStatutCourant = null;

        if(listeStatut != null){
            Optional<Statut> statutCourant = listeStatut.stream()
                    .max(Comparator.comparing(Statut::getDateHeure));

            typeStatutCourant = statutCourant.map(Statut::getTypeStatut).orElse(null);
        }
        return typeStatutCourant;
    }

    public Long getEnseignantId(){
        Long id = 0L;

        if(coursGroupe != null){
            id = coursGroupe.getEnseignant().getId();
        }

        return id;
    }

    public Long getEtudiantId(){
        Long id = 0L;

        if(etudiant != null){
            id = etudiant.getId();
        }
        return id;
    }

}
