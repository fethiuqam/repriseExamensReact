package ca.uqam.repriseexamen.securite.authentification;

import ca.uqam.repriseexamen.model.Permission;
import lombok.Data;

import java.util.Set;

@Data
public class AuthenticationResponse {

    private long id;
    private String type;
    private Set<Permission> permissions;
}
