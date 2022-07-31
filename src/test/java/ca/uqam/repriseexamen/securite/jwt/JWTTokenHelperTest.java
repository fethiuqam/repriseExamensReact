package ca.uqam.repriseexamen.securite.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import javax.servlet.http.Cookie;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JWTTokenHelperTest {

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;


    @Test
    public void testGenererTokenValideCommis() throws Exception {
        String codeMs = "commis";
        String jwtToken = jWTTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(codeMs);
        Boolean tokenValide = jWTTokenHelper.validerToken(jwtToken, userDetails);
        assertTrue(tokenValide);
    }

    @Test
    public void testGenererTokenValideEtudiant1() throws Exception {
        String codeMs = "etudiant1";
        String jwtToken = jWTTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(codeMs);
        Boolean tokenValide = jWTTokenHelper.validerToken(jwtToken, userDetails);
        assertTrue(tokenValide);
    }

    @Test
    public void testComparerTokensValidesEtudiant2() throws Exception {
        String codeMs = "etudiant2";
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        String jwtToken = jWTTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);

        Cookie cookie = new Cookie("token", jwtToken);
        assertNotNull(cookie);
        mockRequest.setCookies(cookie);

        String jwtDeCookie = jWTTokenHelper.getAuthFromCookies(mockRequest);
        assertEquals(jwtToken,jwtDeCookie);
    }

    @Test
    public void testGenererTokenValideEnseignant1() throws Exception {
        String codeMs = "enseignant1";
        String jwtToken = jWTTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(codeMs);
        Boolean tokenValide = jWTTokenHelper.validerToken(jwtToken, userDetails);
        assertTrue(tokenValide);
    }

    @Test
    public void testGenererTokenEnseignant2VerifierDate() throws Exception {
        String codeMs = "enseignant2";
        String jwtToken = jWTTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);

        boolean expirationToken = jWTTokenHelper.tokenEstExpiree(jwtToken);
        assertFalse(expirationToken);

        Date dateToken = jWTTokenHelper.getDateDePublicationDuToken(jwtToken);
        assertNotNull(dateToken);
    }

    @Test
    public void testInvalideJwtDeCookieEnseignant3() throws Exception {
        String codeMs = "enseignant3";
        MockHttpServletRequest MockRequest = new MockHttpServletRequest();
        MockRequest.setParameter("codeMs", codeMs);
        MockRequest.setParameter("motDePasse", "1");

        String jwtDeCookie = jWTTokenHelper.getAuthFromCookies(MockRequest);
        assertNull(jwtDeCookie);
    }

    @Test
    public void testComparerTokensEntreUtilisateursReponseValideFaux() throws Exception {
        String codeMs1 = "etudiant1";
        String codeMs2 = "etudiant2";

        String jwtTokenUser1 = jWTTokenHelper.genererToken(codeMs1);
        UserDetails userDetails2 = userDetailsService.loadUserByUsername(codeMs2);
        Boolean tokenValide = jWTTokenHelper.validerToken(jwtTokenUser1, userDetails2);
        assertFalse(tokenValide);
    }

}
