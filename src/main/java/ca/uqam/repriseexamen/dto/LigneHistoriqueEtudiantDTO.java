package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.MotifAbsence;
import ca.uqam.repriseexamen.model.TypeStatut;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LigneHistoriqueEtudiantDTO {

    @Value("#{target.getId()}")
    long getId();

    @Value("#{target.getDateHeureSoumission()}")
    LocalDateTime getDateHeureSoumission();

    @Value("#{target.getMotifAbsence()}")
    MotifAbsence getMotifAbsence();

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getCoursGroupe().getCours().getSigle() + '-' + target.getCoursGroupe().getGroupe()}")
    String getCoursGroupe();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatut();
    
}
