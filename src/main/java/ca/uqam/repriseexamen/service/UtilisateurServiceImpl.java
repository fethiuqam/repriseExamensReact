package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.RoleRepository;
import ca.uqam.repriseexamen.dao.UtilisateurRepository;
import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    private final RoleRepository roleRepository;

    @Override
    public Utilisateur TrouverParCodeMs(String codeMs) {
        return utilisateurRepository.findByCodeMsEquals(codeMs);
    }

    @Override
    public Utilisateur sauvegarderUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Role sauvegarderRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void ajouterRoleAUtilisateur(String codeMS, String nomRole) {
        Utilisateur utilisateur = utilisateurRepository.findByCodeMsEquals(codeMS);
        Role role = roleRepository.findByNomEquals(nomRole);
        utilisateur.getRoles().add(role);
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Utilisateur> getUtilisateurList()
    {
        return utilisateurRepository.findAll();
    }
}
