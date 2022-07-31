package ca.uqam.repriseexamen.securite;

import ca.uqam.repriseexamen.model.Permission;
import ca.uqam.repriseexamen.model.Role;
import ca.uqam.repriseexamen.model.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UtilisateurAuthentifieServiceTest {

    @Autowired
    private UtilisateurAuthentifieService authentifieService;


    @WithMockUser(value = "commis", password = "1")
    @Test
    public void testCommisAuthentifie() throws Exception {
        Utilisateur commis = authentifieService.GetAuthentifie();
        assertNotNull(commis);
    }

    @WithMockUser(value = "commis", password = "1")
    @Test
    public void testReponseRolePermissionCommis() throws Exception {
        Utilisateur commis = authentifieService.GetAuthentifie();

        Set<Role> roles = commis.getRoles();
        assertNotNull(roles);
        Role role = roles.iterator().next();
        assertTrue(authentifieService.AuthentifieContientRole(role.getNom()));

        Set<Permission> permissions = commis.getPermissions();
        assertNotNull(permissions);
        Permission permission = permissions.iterator().next();
        assertTrue(authentifieService.AuthentifieContientPermission(permission.name()));
    }

    @WithMockUser(value = "enseignant1", password = "1")
    @Test
    public void testReponseRolePermissionEnseignant1() throws Exception {
        Utilisateur enseignant1 = authentifieService.GetAuthentifie();

        Set<Role> roles = enseignant1.getRoles();
        if (!roles.isEmpty()) {
            Role role = roles.iterator().next();
            assertTrue(authentifieService.AuthentifieContientRole(role.getNom()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientRole("Pas de role"));
        }

        Set<Permission> permissions = enseignant1.getPermissions();
        if (!permissions.isEmpty()) {
            Permission permission = permissions.iterator().next();
            assertTrue(authentifieService.AuthentifieContientPermission(permission.name()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientPermission("Pas de permission"));
        }
    }

    @WithMockUser(value = "enseignant2", password = "1")
    @Test
    public void testReponseRolePermissionEnseignant2() throws Exception {
        Utilisateur enseignant2 = authentifieService.GetAuthentifie();

        Set<Role> roles = enseignant2.getRoles();
        if (!roles.isEmpty()) {
            Role role = roles.iterator().next();
            assertTrue(authentifieService.AuthentifieContientRole(role.getNom()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientRole("Pas de role"));
        }

        Set<Permission> permissions = enseignant2.getPermissions();
        if (!permissions.isEmpty()) {
            Permission permission = permissions.iterator().next();
            assertTrue(authentifieService.AuthentifieContientPermission(permission.name()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientPermission("Pas de permission"));
        }
    }

    @WithMockUser(value = "etudiant1", password = "1")
    @Test
    public void testReponseRolePermissionEtudiant1() throws Exception {
        Utilisateur etudiant1 = authentifieService.GetAuthentifie();

        Set<Role> roles = etudiant1.getRoles();
        if (!roles.isEmpty()) {
            Role role = roles.iterator().next();
            assertTrue(authentifieService.AuthentifieContientRole(role.getNom()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientRole("Pas de role"));
        }

        Set<Permission> permissions = etudiant1.getPermissions();
        if (!permissions.isEmpty()) {
            Permission permission = permissions.iterator().next();
            assertTrue(authentifieService.AuthentifieContientPermission(permission.name()));
        }
        else {
            assertFalse(authentifieService.AuthentifieContientPermission("Pas de permission"));
        }
    }

}
