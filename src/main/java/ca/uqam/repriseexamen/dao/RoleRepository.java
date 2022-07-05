package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
