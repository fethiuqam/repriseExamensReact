package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Session;
import ca.uqam.repriseexamen.model.TypeStatut;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import org.springframework.beans.factory.annotation.Value;
import java.time.LocalDateTime;

@JsonSerializableSchema
public interface LigneDRECommisDTO {

    @Value("#{target.getId()}")
    long getId();

    @Value("#{target.getDateHeureSoumission()}")
    LocalDateTime getDateHeureSoumission();

    @Value("#{target.getStatutCourant()}")
    TypeStatut getStatutCourant();

    @Value("#{target.getEtudiant().getPrenom() + ' ' + target.getEtudiant().getNom()}")
    String getNomEtudiant();

    @Value("#{target.getEtudiant().getCodePermanent()}")
    String getCodePermanentEtudiant();

    @Value("#{target.getCoursGroupe().getEnseignant().getPrenom() + ' ' + target.getCoursGroupe().getEnseignant().getNom()}")
    String getNomEnseignant();

    @Value("#{target.getCoursGroupe().getEnseignant().getMatricule()}")
    String getMatriculeEnseignant();

    @Value("#{target.getCoursGroupe().getCours().getSigle()}")
    String getSigleCours();

    @Value("#{target.getCoursGroupe().getGroupe()}")
    String getGroupe();

    @Value("#{target.getCoursGroupe().getSession()}")
    Session getSession();

}
