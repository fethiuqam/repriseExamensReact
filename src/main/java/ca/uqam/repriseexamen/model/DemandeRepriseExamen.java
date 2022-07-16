package ca.uqam.repriseexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeRepriseExamen {

    // Attributs

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate absenceDateDebut;
    private LocalDate absenceDateFin;
    private MotifAbsence motifAbsence;
    private String absenceDetails;
    private String descriptionExamen;

    @JsonManagedReference(value = "listeStatut")
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Statut> listeStatut;

    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Decision> listeDecision;

    @JsonBackReference(value = "demandesEtudiant")
    @ManyToOne
    private Etudiant etudiant;

    @JsonManagedReference(value = "listeJustification")
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Justification> listeJustification;

    @ManyToOne
    private CoursGroupe coursGroupe;
    @OneToMany(mappedBy = "demandeRepriseExamen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> listeMessage;

    // Méthodes publiques

    @JsonIgnore
    public LocalDateTime getDateHeureSoumission() {
        Optional<Statut> statutSoumission = listeStatut.stream()
                .filter(o -> o.getTypeStatut().equals(TypeStatut.SOUMISE))
                .findFirst();

        return statutSoumission.map(Statut::getDateHeure).orElse(null);
    }

    @JsonIgnore
    public TypeStatut getStatutCourant() {
        Optional<Statut> statutCourant = listeStatut.stream()
                .max(Comparator.comparing(Statut::getDateHeure));

        return statutCourant.map(Statut::getTypeStatut).orElse(null);
    }

    @JsonIgnore
    public TypeDecision getDecisionCourante() {
        Optional<Decision> decisionCourante = listeDecision.stream()
                .max(Comparator.comparing(Decision::getDateHeure));

        return decisionCourante.map(Decision::getTypeDecision).orElse(null);
    }

    @JsonIgnore
    public TypeMessage getTypeMessageCourant() {
        Optional<Message> dernierMessage = listeMessage.stream()
                .max(Comparator.comparing(Message::getDateHeure));

        return dernierMessage.map(Message::getTypeMessage).orElse(null);
    }
}
