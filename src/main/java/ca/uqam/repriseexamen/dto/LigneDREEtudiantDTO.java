package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Justification;
import ca.uqam.repriseexamen.model.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface LigneDREEtudiantDTO extends LigneDREDTO {

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getListeJustification()}")
    List<Justification> getJustifications();

    @Value("#{target.getListeMessage()}")
    List<Message> getListeMessage();

    @Value("#{ target.estRejetee() ? target.getDecisionCourante().getDetails() : null}")
    String getDetailsRejet();

    @JsonIgnore
    @Value("#{target.getEtudiant().getId()}")
    long getIdEtudiant();

}
