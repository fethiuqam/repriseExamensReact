package ca.uqam.repriseexamen.securite;

import ca.uqam.repriseexamen.dao.UtilisateurRepository;
import ca.uqam.repriseexamen.model.Permission;
import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InformationUtilisateurServiceImpl implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String codeMs) throws UsernameNotFoundException {
        Utilisateur user = utilisateurRepository.findByCodeMsEquals(codeMs);

        if (user == null)
            throw new UsernameNotFoundException("CodeMs not found");

        return new org.springframework.security.core.userdetails.User(
                user.getCodeMs(), user.getMotDePasse(), true, true, true,
                true, getAllPermissons(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAllPermissons(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPermissions());
        }
        for (Permission item : collection) {
            permissions.add(item.name());
        }
        return permissions;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


}
