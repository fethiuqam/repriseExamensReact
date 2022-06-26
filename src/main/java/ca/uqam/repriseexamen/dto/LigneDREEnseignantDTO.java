package ca.uqam.repriseexamen.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

public interface LigneDREEnseignantDTO extends LigneDREDTO {

    @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
    String getNomEtudiant();

    @Value("#{target.getEtudiant().getCodePermanent()}")
    String getCodePermanentEtudiant();

    @JsonIgnore
    @Value("#{target.getCoursGroupe().getEnseignant().getId()}")
    Long getEnseignantId();

}
