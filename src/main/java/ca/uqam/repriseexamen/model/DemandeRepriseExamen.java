package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DemandeRepriseExamen {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate absenceDateDebut;
    private LocalDate absenceDateFin;
    private MotifAbsence motifAbsence;
    private String absenceDetails;
    private String descriptionExamen;
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.PERSIST)
    private List<Statut> listeStatut;
    @ManyToOne
    private Etudiant etudiant;
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.PERSIST)
    private List<Justification> listeJustification;
    @ManyToOne
    private CoursGroupe coursGroupe;

    public LocalDateTime getDateHeureSoumission(){
        Optional<Statut> statutSoumission = listeStatut.stream()
                .filter(o -> o.getTypeStatut().equals(TypeStatut.SOUMISE))
                .findFirst();

        return statutSoumission.map(Statut::getDateHeure).orElse(null);
    }

    public TypeStatut getStatutCourant(){
        Optional<Statut> statutCourant = listeStatut.stream()
                .max(Comparator.comparing(Statut::getDateHeure));
        return statutCourant.map(Statut::getTypeStatut).orElse(null);
    }

}
