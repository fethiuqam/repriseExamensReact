package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Justification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface LigneDREEtudiantDTO extends LigneDREDTO {

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getListeJustification()}")
    List<Justification> getJustifications();

    @JsonIgnore
    @Value("#{target.getEtudiant().getId()}")
    long getIdEtudiant();

}
