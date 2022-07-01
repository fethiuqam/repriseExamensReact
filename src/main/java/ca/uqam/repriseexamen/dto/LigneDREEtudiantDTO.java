package ca.uqam.repriseexamen.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;

public interface LigneDREEtudiantDTO extends LigneDREDTO {

    @Value("#{target.getCoursGroupe().getEnseignant().getPrenom() + ' ' + target.getCoursGroupe().getEnseignant().getNom()}")
    String getNomEnseignant();

    @JsonIgnore
    @Value("#{target.getEtudiant().getId()}")
    Long getEtudiantId();

}
