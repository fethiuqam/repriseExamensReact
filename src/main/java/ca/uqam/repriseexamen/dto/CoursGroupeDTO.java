package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = { CoursGroupe.class })
public interface CoursGroupeDTO {

    Long getId();

    Cours getCours();

    Enseignant getEnseignant();

    Reprise getReprise();

    String getGroupe();

    Session getSession();

    String getAnnee();

    @JsonIgnore
    List<DemandeRepriseExamen> getDemandes();

}
