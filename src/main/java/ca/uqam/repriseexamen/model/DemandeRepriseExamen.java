package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DemandeRepriseExamen {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate absenceDateDebut;
    private LocalDate absenceDateFin;
    private MotifAbsence motifAbsence;
    private String absenceDetails;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Statut> listeStatut;
    @ManyToOne
    private Etudiant detenteur;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Justification> listeJustification;



}
