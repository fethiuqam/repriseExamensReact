package ca.uqam.repriseexamen.securite;

import ca.uqam.repriseexamen.model.Permission;
import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;
import ca.uqam.repriseexamen.service.UtilisateurServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UtilisateurAuthentifieService {

    UtilisateurServiceImpl utilisateurService;

    public Utilisateur GetAuthentifie() throws Exception
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameCourant = authentication.getName();

        if(usernameCourant.isEmpty())
            throw new Exception("Aucun utilisateur authentifie");

        return utilisateurService.TrouverParCodeMs(usernameCourant);
    }

    public boolean AuthentifieContientRole(String nomRole)
    {
        boolean contient = false;
        try{
            Utilisateur utilisateur = GetAuthentifie();
            contient = contientRole(nomRole,utilisateur.getRoles());
        }catch (Exception e)
        {
            return contient;
        }
        return contient;
    }

    public boolean AuthentifieContientPermission(String nomPermission)
    {
        boolean contient = false;
        try{
            Utilisateur utilisateur = GetAuthentifie();
            contient = contientPermission(nomPermission,utilisateur.getRoles());
        }catch (Exception e)
        {
            return contient;
        }
        return contient;
    }

    private boolean contientRole(String nom,Collection<Role> roles)
    {
        boolean contient = false;

        for (Role role:roles) {
            if(role.getNom().equals(nom)) {
                contient = true;
                break;
            }
        }

        return contient;
    }

    private boolean contientPermission(String nom,Collection<Role> roles)
    {
        boolean contient = false;

        for (Role role:roles) {
            for(Permission permission: role.getPermissions())
            {
                if(permission.name().equals(nom)) {
                    contient = true;
                    break;
                }
            }
        }

        return contient;
    }


}
