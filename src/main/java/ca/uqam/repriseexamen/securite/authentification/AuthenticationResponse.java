package ca.uqam.repriseexamen.securite.authentification;

import ca.uqam.repriseexamen.model.Permission;
import lombok.Data;

import java.util.Set;

@Data
public class AuthenticationResponse {

    private String token;
    private long id;
    private String type;
    private Set<Permission> permissions;
}
