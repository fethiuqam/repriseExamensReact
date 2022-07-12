package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur TrouverParCodeMs(String codeMs);
    Utilisateur sauvegarderUtilisateur(Utilisateur utilisateur);
    Role sauvegarderRole(Role role);
    void ajouterRoleAUtilisateur(String codeMS,String nomRole);
    List<Utilisateur> getUtilisateurList(); //TODO: change in the futur will load every User!

}
