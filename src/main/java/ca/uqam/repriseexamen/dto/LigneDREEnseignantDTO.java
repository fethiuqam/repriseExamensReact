package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.TypeDecision;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

public interface LigneDREEnseignantDTO extends LigneDREDTO {

    @JsonIgnore
    @Value("#{target.getDecisionCourante()}")
    TypeDecision getDecision();

    @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
    String getNomEtudiant();

    @Value("#{target.getEtudiant().getCodePermanent()}")
    String getCodePermanentEtudiant();

}
