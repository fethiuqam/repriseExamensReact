package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface LigneDREDTO {

    @Value("#{target.getId()}")
    long getId();

    @Value("#{target.getDateHeureSoumission()}")
    LocalDateTime getDateHeureSoumission();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatutCourant();

    @Value("#{target.getAbsenceDateDebut()}")
    LocalDate getAbsenceDateDebut();

    @Value("#{target.getAbsenceDateFin()}")
    LocalDate getAbsenceDateFin();

    @Value("#{target.getMotifAbsence()}")
    MotifAbsence getMotifAbsence();

    @Value("#{target.getDescriptionExamen()}")
    String getExamen();

    @Value("#{target.getEtudiant()}")
    Etudiant getEtudiant();

    @Value("#{target.getCoursGroupe().getEnseignant()}")
    Enseignant getEnseignant();

    @Value("#{target.getCoursGroupe()}")
    CoursGroupe getCoursGroupe();
}
