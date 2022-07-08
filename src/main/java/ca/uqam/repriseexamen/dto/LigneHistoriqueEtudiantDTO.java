package ca.uqam.repriseexamen.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ca.uqam.repriseexamen.model.MotifAbsence;
import ca.uqam.repriseexamen.model.TypeStatut;

public interface LigneHistoriqueEtudiantDTO {

    @Value("#{target.getId()}")
    long getId();

    @JsonIgnore
    @Value("#{target.getEtudiant().getId()}")
    long getEtudiantId();

    @Value("#{target.getDateHeureSoumission()}")
    LocalDateTime getDateHeureSoumission();

    @Value("#{target.getMotifAbsence()}")
    MotifAbsence getMotifAbsence();

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getCoursGroupe().getCours().getSigle() + '-' + target.getCoursGroupe().getGroupe()}")
    String getCoursGroupe();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatutCourant();
    
}
