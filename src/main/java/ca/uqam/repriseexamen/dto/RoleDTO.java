package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Permission;
import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {Role.class})
public interface RoleDTO {
    Long getId();

    List<Permission> getPermissions();

    String getNom();
}
