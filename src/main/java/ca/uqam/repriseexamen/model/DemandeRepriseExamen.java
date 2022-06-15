package ca.uqam.repriseexamen.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@RestResource(path = "demandes")
public class DemandeRepriseExamen {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate absenceDateDebut;
    private LocalDate absenceDateFin;
    private MotifAbsence motifAbsence;
    private String absenceDetails;
    private String descriptionExamen;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Statut> listeStatut;
    @ManyToOne
    private Etudiant etudiant;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Justification> listeJustification;
    @ManyToOne
    private CoursGroupe coursGroupe;

    @Projection(
        name = "demandeCommis",
        types = { DemandeRepriseExamen.class }
    )
    interface DemandeRepriseExamenCommis {

        long getId();

        @Value("#{target.getListeStatut()[target.getListeStatut().size-1].getDateHeure()}")
        LocalDateTime getDateHeureSoumission();

        @Value("#{target.getListeStatut()[0].getTypeStatut()}")
        TypeStatut getStatutCourant();

        @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
        String getNomEtudiant();

        @Value("#{target.getEtudiant().getCodePermanent()}")
        String getCodePermanentEtudiant();

        @Value("#{target.getCoursGroupe().getEnseignant().getPrenom() + ' ' target.getCoursGroupe().getEnseignant().getNom()}")
        String getNomEnseignant();

        @Value("#{target.getCoursGroupe().getEnseignant().getMatricule()}")
        String getMatriculeEnseignant();

        @Value("#{target.getCoursGroupe().getCours().getSigle()}")
        String getSigleCours();

        @Value("#{target.getCoursGroupe().getGroupe()}")
        String getGroupe();

        @Value("#{target.getCoursGroupe().getSession()}")
        Session getSession();
    }

}
