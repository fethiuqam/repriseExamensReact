package ca.uqam.repriseexamen.dao;

import ca.uqam.repriseexamen.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
