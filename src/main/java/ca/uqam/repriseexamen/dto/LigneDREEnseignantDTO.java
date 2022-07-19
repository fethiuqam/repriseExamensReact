package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.TypeDecision;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

public interface LigneDREEnseignantDTO extends LigneDREDTO {

    @Value("#{target.getDecisionCourante()}")
    TypeDecision getDecisionCourante();

    @JsonIgnore
    @Value("#{target.getCoursGroupe().getEnseignant().getId()}")
    long getIdEnseignant();
}
