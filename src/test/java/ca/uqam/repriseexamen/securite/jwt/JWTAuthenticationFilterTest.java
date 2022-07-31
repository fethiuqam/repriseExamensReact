package ca.uqam.repriseexamen.securite.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import javax.servlet.http.Cookie;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JWTAuthenticationFilterTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;


    @Test
    public void testAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);
        assertNotNull(filter);
    }

    @Test
    public void testAppeldoFilterInternalUneFois() throws Exception {
        JWTAuthenticationFilter filterMock = mock(JWTAuthenticationFilter.class,
                withSettings().useConstructor(userDetailsService,jwtTokenHelper));

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockChain = new MockFilterChain();

        filterMock.doFilterInternal(mockRequest, mockResponse, mockChain);
        verify(filterMock, times(1)).doFilterInternal(mockRequest, mockResponse, mockChain);
    }

    @Test
    public void testDoFilterInternalAffecteFilterChainSansToken() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockFilterChain = new MockFilterChain();

        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNotNull(mockFilterChain.getRequest());
        assertNotNull(mockFilterChain.getResponse());
        assertEquals(mockRequest, mockFilterChain.getRequest());
        assertEquals(mockResponse, mockFilterChain.getResponse());
    }

    @Test
    public void testDoFilterInternalAffecteFilterChainPourCommis() throws Exception {
        String codeMs = "commis";

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockChain = new MockFilterChain();

        String jwtToken = jwtTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);
        Cookie cookie = new Cookie("token", jwtToken);
        assertNotNull(cookie);
        mockRequest.setCookies(cookie);

        filter.doFilterInternal(mockRequest, mockResponse, mockChain);

        assertNotNull(mockChain.getRequest());
        assertNotNull(mockChain.getResponse());
        assertEquals(mockRequest, mockChain.getRequest());
        assertEquals(mockResponse, mockChain.getResponse());
    }

    @Test
    public void testDoFilterInternalAffecteFilterChainPourEnseignant1() throws Exception {
        String codeMs = "enseignant1";

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockChain = new MockFilterChain();

        String jwtToken = jwtTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);
        Cookie cookie = new Cookie("token", jwtToken);
        assertNotNull(cookie);
        mockRequest.setCookies(cookie);

        filter.doFilterInternal(mockRequest, mockResponse, mockChain);

        assertNotNull(mockChain.getRequest());
        assertNotNull(mockChain.getResponse());
    }

    @Test
    public void testDoFilterInternalAffecteFilterChainPourEtudiant1() throws Exception {
        String codeMs = "etudiant1";

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockChain = new MockFilterChain();

        String jwtToken = jwtTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);
        Cookie cookie = new Cookie("token", jwtToken);
        assertNotNull(cookie);
        mockRequest.setCookies(cookie);

        filter.doFilterInternal(mockRequest, mockResponse, mockChain);

        assertNotNull(mockChain.getRequest());
        assertNotNull(mockChain.getResponse());
    }

    @Test
    public void testDeuxAppelsDoFilterInternalAffecteFilterChainPourEtudiant2() throws Exception {
        String codeMs = "etudiant2";

        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, jwtTokenHelper);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
        MockFilterChain mockChain = new MockFilterChain();
        MockFilterChain mockChain2 = new MockFilterChain();

        String jwtToken = jwtTokenHelper.genererToken(codeMs);
        assertNotNull(jwtToken);
        Cookie cookie = new Cookie("token", jwtToken);
        assertNotNull(cookie);
        mockRequest.setCookies(cookie);

        filter.doFilterInternal(mockRequest, mockResponse, mockChain);
        filter.doFilterInternal(mockRequest, mockResponse, mockChain2);

        assertEquals(mockChain.getRequest(),mockChain2.getRequest());
        assertEquals(mockChain.getResponse(),mockChain2.getResponse());
    }

}
