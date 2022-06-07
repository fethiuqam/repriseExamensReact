package ca.uqam.repriseexamen;

import ca.uqam.repriseexamen.dao.ExamRetakeRequestRepository;
import ca.uqam.repriseexamen.dao.StudentRepository;
import ca.uqam.repriseexamen.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class RepriseexamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepriseexamenApplication.class, args);
	}

	@Bean
	CommandLineRunner generateDataForTest(ExamRetakeRequestRepository examRetakeRequestRepository,
										  StudentRepository studentRepository) {
		return args -> {
			Stream.of("Marc", "Richard", "Jean").forEach(name -> {
				Student student = Student.builder()
						.name(name)
						.permanentCode("ABCD12345678")
						.email(name + "@courrier.uqam.ca")
						.phone("1111111111")
						.build();
				studentRepository.save(student);
			});
			studentRepository.findAll().forEach(student -> {
				Justificative justificative = Justificative.builder()
						.description("justificatif 1")
						.url("/files/file1")
						.build();
				List<Justificative> justificatives = Arrays.asList(justificative);
				Status status = Status.builder()
						.typeStatus(TypeStatus.SUBMITTED)
						.dateTime(LocalDateTime.now())
						.build();
				List<Status> statusList = Arrays.asList(status);
				ExamRetakeRequest dre = ExamRetakeRequest.builder()
						.absenceStartDate(LocalDate.of(2022,2,2))
						.absenceEndDate(LocalDate.of(2022,2,10))
						.owner(student)
						.listJustificative(justificatives)
						.reason(Reason.MEDICAL)
						.statusList(statusList)
						.absenceDetails("Intervention chirurgicale programmée")
						.build();
				examRetakeRequestRepository.save(dre);
			});
		};
	}

}
