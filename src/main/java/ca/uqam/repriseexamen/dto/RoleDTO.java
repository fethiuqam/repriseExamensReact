package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { Role.class })
public interface RoleDTO {
    Long getId();

    String getNom();
}
