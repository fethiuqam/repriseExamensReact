package ca.uqam.repriseexamen.securite.authentification;

import ca.uqam.repriseexamen.model.Permission;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
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
public class AuthenticationResponseTest {

    @Autowired
    private UtilisateurAuthentifieService authentifieService;


    @WithMockUser(value = "commis", password = "1")
    @Test
    public void testReponseIdTypePermissionsCommis() throws Exception {
        AuthenticationResponse response= new AuthenticationResponse();
        response.setId(authentifieService.GetAuthentifie().getId());
        response.setType(authentifieService.GetAuthentifie().getType());
        response.setPermissions(authentifieService.GetAuthentifie().getPermissions());
        assertNotNull(response);
        assertTrue(response.getId() > 0, "L'identifiant doit être supérieur à 0");
        assertEquals(response.getType(), "personnel");

        Set<Permission> permissions =
                Set.of(Permission.JugerCommis, Permission.AfficherDRE, Permission.ListerDRE,
                        Permission.PlanifierDates, Permission.JugerEnseignant, Permission.AfficherJustificatifs,
                        Permission.RetournerDemande, Permission.ArchiverDemande);

        assertEquals(permissions, response.getPermissions());
    }

    @WithMockUser(value = "enseignant1", password = "1")
    @Test
    public void testReponseIdTypePermissionsEnseignant1() throws Exception {
        AuthenticationResponse response= new AuthenticationResponse();
        response.setId(authentifieService.GetAuthentifie().getId());
        response.setType(authentifieService.GetAuthentifie().getType());
        response.setPermissions(authentifieService.GetAuthentifie().getPermissions());

        assertNotNull(response);
        assertTrue(response.getId() > 0, "L'identifiant doit être supérieur à 0");
        assertEquals(response.getType(), "enseignant");

        Set<Permission> permissions = Set.of();
        assertEquals(permissions, response.getPermissions());
    }

    @WithMockUser(value = "etudiant1", password = "1")
    @Test
    public void testReponseIdTypePermissionsEtudiant1() throws Exception {
        AuthenticationResponse response= new AuthenticationResponse();
        response.setId(authentifieService.GetAuthentifie().getId());
        response.setType(authentifieService.GetAuthentifie().getType());
        response.setPermissions(authentifieService.GetAuthentifie().getPermissions());

        assertNotNull(response);
        assertTrue(response.getId() > 0, "L'identifiant doit être supérieur à 0");
        assertEquals(response.getType(), "etudiant");

        Set<Permission> permissions = Set.of();
        assertEquals(permissions, response.getPermissions());
    }
}
