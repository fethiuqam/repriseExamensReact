package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface LigneDREPersonnelDTO extends LigneDREDTO {

    @Value("#{target.getDecisionCourante()}")
    Decision getDecisionCourante();

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getListeJustification()}")
    List<Justification> getJustifications();

    @Value("#{target.getListeStatut()}")
    List<Statut> getListeStatut();

    @Value("#{target.getListeDecision()}")
    List<Decision> getListeDecision();

    @Value("#{target.getListeMessage()}")
    List<Message> getListeMessage();

}
