package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Justification;
import ca.uqam.repriseexamen.model.TypeDecision;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface LigneDREPersonnelDTO extends LigneDREDTO {

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

    @Value("#{target.getAbsenceDetails()}")
    String getAbsenceDetails();

    @Value("#{target.getListeJustification()}")
    List<Justification> getJustifications();


}
