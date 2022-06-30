package ca.uqam.repriseexamen.dto;

import org.springframework.beans.factory.annotation.Value;

public interface LigneDREEtudiantDTO extends LigneDREDTO {

    @Value("#{target.getCoursGroupe().getEnseignant().getPrenom() + ' ' + target.getCoursGroupe().getEnseignant().getNom()}")
    String getNomEnseignant();

    @Value("#{target.getCoursGroupe().getEnseignant().getMatricule()")
    String getMatriculeEnseignant();

}
