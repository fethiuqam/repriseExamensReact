package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.*;
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

    @Value("#{target.getCoursGroupe()}")
    CoursGroupe getCoursGroupe();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatutCourant();

    @Value("#{target.getDecisionCourante()}")
    Decision getDecisionCourante();

    
}
