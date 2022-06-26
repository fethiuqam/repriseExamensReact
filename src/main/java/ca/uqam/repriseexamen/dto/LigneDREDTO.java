package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Session;
import ca.uqam.repriseexamen.model.TypeStatut;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LigneDREDTO {

    @Value("#{target.getId()}")
    long getId();

    @Value("#{target.getDateHeureSoumission()}")
    LocalDateTime getDateHeureSoumission();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatutCourant();

    @Value("#{target.getCoursGroupe().getCours().getSigle()}")
    String getSigleCours();

    @Value("#{target.getCoursGroupe().getGroupe()}")
    String getGroupe();

    @Value("#{target.getCoursGroupe().getSession()}")
    Session getSession();

}
