package ca.uqam.repriseexamen;

import ca.uqam.repriseexamen.dao.RetakeExamRequestRepository;
import ca.uqam.repriseexamen.dao.StudentRepository;
import ca.uqam.repriseexamen.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class RepriseexamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepriseexamenApplication.class, args);
	}

	@Bean
	CommandLineRunner generateDataForTest(RetakeExamRequestRepository retakeExamRequestRepository,
							StudentRepository studentRepository) {
		return args -> {
			Stream.of("Marc", "Richard", "Jean").forEach(name -> {
				Student student = new Student();
				student.setName(name);
				student.setPermanentCode("ABCD12345678");
				student.setEmail(name + "@courrier.uqam.ca");
				student.setPhone("1111111111");
				studentRepository.save(student);
			});
			studentRepository.findAll().forEach(student -> {
				RetakeExamRequest dre = new RetakeExamRequest();
				dre.setAbsenceStartDate(LocalDate.of(2022,02,02));
				dre.setAbsenceEndDate(LocalDate.of(2022,02,10));
				dre.setOwner(student);
				Justificative justificative = new Justificative();
				justificative.setDescription("justificatif 1");
				justificative.setUrl("/files/file1");
				List<Justificative> justificatives =  new ArrayList<>();
				justificatives.add(justificative);
				dre.setListJustificative(justificatives);
				dre.setReason(Reason.MEDICAL);
				Status status = new Status();
				status.setTypeStatus(TypeStatus.SUBMITTED);
				status.setDateTime(LocalDateTime.now());
				List<Status> statusList = new ArrayList<>();
				statusList.add(status);
				dre.setStatusList(statusList);
				dre.setAbsenceDetails("Intervention chirurgicale programmée");
				retakeExamRequestRepository.save(dre);
			});
		};
	}

}
