package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(types = { Utilisateur.class })
public interface UtilisateurDTO {

    String getNom();

    String getPrenom();

    String getEmail();

    String getMatricule();

    Set<Role> getRoles();

    String getType();
}
