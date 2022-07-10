package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.TypeDecision;
import org.springframework.beans.factory.annotation.Value;

public interface LigneDRECommisDTO extends LigneDREDTO {

    @Value("#{target.getDecisionCourante()}")
    TypeDecision getDecision();

    @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
    String getNomEtudiant();

    @Value("#{target.getEtudiant().getCodePermanent()}")
    String getCodePermanentEtudiant();

    @Value("#{target.getCoursGroupe().getEnseignant().getPrenom() " +
            "+ ' ' + target.getCoursGroupe().getEnseignant().getNom()}")
    String getNomEnseignant();

    @Value("#{target.getCoursGroupe().getEnseignant().getMatricule()}")
    String getMatriculeEnseignant();

}
