package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
