package ca.uqam.repriseexamen.securite.authentification;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;
    public long id;
    public String type;
}
