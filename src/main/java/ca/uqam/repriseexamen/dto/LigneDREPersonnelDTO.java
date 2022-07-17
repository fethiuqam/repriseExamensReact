package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Decision;
import ca.uqam.repriseexamen.model.Justification;
import ca.uqam.repriseexamen.model.Statut;
import ca.uqam.repriseexamen.model.TypeDecision;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface LigneDREPersonnelDTO extends LigneDREDTO {

    @Value("#{target.getDecisionCourante()}")
    TypeDecision getDecisionCourante();

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getListeJustification()}")
    List<Justification> getJustifications();

    @Value("#{target.getListeStatut()}")
    List<Statut> getListeStatut();

    @Value("#{target.getListeDecision()}")
    List<Decision> getListeDecision();

}
