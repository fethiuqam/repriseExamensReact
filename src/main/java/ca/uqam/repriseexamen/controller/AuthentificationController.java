package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.securite.InformationUtilisateurServiceImpl;
import ca.uqam.repriseexamen.securite.UtilisateurAuthentifieService;
import ca.uqam.repriseexamen.securite.authentification.AuthenticationRequest;
import ca.uqam.repriseexamen.securite.authentification.AuthenticationResponse;
import ca.uqam.repriseexamen.securite.jwt.JWTTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthentificationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurAuthentifieService authentifieService;

    @Autowired
    InformationUtilisateurServiceImpl informationUtilisateurService;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getCodeMs(), authenticationRequest.getMotDePasse()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user= (User) authentication.getPrincipal();
        String jwtToken = jWTTokenHelper.genererToken(user.getUsername());

        AuthenticationResponse response= new AuthenticationResponse();
        response.setId(authentifieService.GetAuthentifie().getId());
        response.setType(authentifieService.GetAuthentifie().getType());
        response.setPermissions(authentifieService.GetAuthentifie().getPermissions());

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setMaxAge(60*60);
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.addCookie(cookie);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/validCookie")
    public ResponseEntity<?> validCookie(HttpServletRequest request, HttpServletResponse res) throws Exception {
        String token = jWTTokenHelper.getToken(request);
        if(token != null)
        {
            String username = jWTTokenHelper.getUsernameDuToken(token);
            UserDetails details = informationUtilisateurService.loadUserByUsername(username);
            if(jWTTokenHelper.validerToken(token,details))
            {
                return ResponseEntity.ok().body("");
            }
        }
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse res) throws Exception {

        Cookie supprimerTokenCookie = new Cookie("token", "");
        supprimerTokenCookie.setMaxAge(0);
        supprimerTokenCookie.setPath("/");
        supprimerTokenCookie.setSecure(false);
        supprimerTokenCookie.setHttpOnly(true);
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.addCookie(supprimerTokenCookie);

        return ResponseEntity.ok().body("");
    }
}

