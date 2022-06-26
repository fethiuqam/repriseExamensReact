package ca.uqam.repriseexamen.dtomapper;

import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class DemandeRepriseExamenMapper {

    public LigneDREEtudiantDTO ligneDREEtudiantMapper(DemandeRepriseExamen dre) {
        Optional<Statut> statutSoumission = dre.getListeStatut().stream()
                .filter(o -> o.getTypeStatut().equals(TypeStatut.SOUMISE))
                .findFirst();

        LocalDateTime dateHeureSoumission = statutSoumission.map(Statut::getDateHeure).orElse(null);
        Optional<Statut> statutCourant = dre.getListeStatut().stream()
                .max(Comparator.comparing(Statut::getDateHeure));
        TypeStatut typeStatutCourant = statutCourant.map(Statut::getTypeStatut).orElse(null);

        Enseignant enseignant = dre.getCoursGroupe().getEnseignant();
        CoursGroupe coursGroupe = dre.getCoursGroupe();

        return LigneDREEtudiantDTO.builder()
                .id(dre.getId())
                .dateHeureSoumission(dateHeureSoumission)
                .statutCourant(typeStatutCourant)
                .nomEnseignant(enseignant.getPrenom() + " " + enseignant.getNom())
                .matriculeEnseignant(enseignant.getMatricule())
                .sigleCours(coursGroupe.getCours().getSigle())
                .groupe(coursGroupe.getGroupe())
                .session(coursGroupe.getSession())
                .build();
    }
}
