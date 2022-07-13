package ca.uqam.repriseexamen.controller;

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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthentificationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurAuthentifieService authentifieService;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getCodeMs(), authenticationRequest.getMotDePasse()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user= (User) authentication.getPrincipal();
        String jwtToken=jWTTokenHelper.genererToken(user.getUsername());

        AuthenticationResponse response= new AuthenticationResponse();
        response.setToken(jwtToken);
        response.setId(authentifieService.GetAuthentifie().getId());
        response.setType(authentifieService.GetAuthentifie().getType());

        return ResponseEntity.ok(response);
    }
}

