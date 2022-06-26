package ca.uqam.repriseexamen.dto;

import org.springframework.beans.factory.annotation.Value;

public interface LigneDRECommisDTO extends LigneDREDTO {

    @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
    String getNomEtudiant();

    @Value("#{target.getEtudiant().getCodePermanent()}")
    String getCodePermanentEtudiant();

    @Value("#{target.getCoursGroupe().getEnseignant().getMatricule()}")
    String getMatriculeEnseignant();

}
