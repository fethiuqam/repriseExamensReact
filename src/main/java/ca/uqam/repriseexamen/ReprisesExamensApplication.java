package ca.uqam.repriseexamen;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.EtudiantRepository;
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
public class ReprisesExamensApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReprisesExamensApplication.class, args);
	}

	@Bean
	CommandLineRunner generateDataForTest(DemandeRepriseExamenRepository DemandeRepriseExamenRepository,
										  EtudiantRepository etudiantRepository) {
		return args -> {
			Stream.of("Marc", "Richard", "Jean").forEach(nom -> {
				Etudiant etudiant = Etudiant.builder()
						.nom(nom)
						.codePermanent("ABCD12345678")
						.email(nom + "@courrier.uqam.ca")
						.telephone("1111111111")
						.build();
				etudiantRepository.save(etudiant);
			});
			etudiantRepository.findAll().forEach(etudiant -> {
				Justification justification = Justification.builder()
						.description("justificatif 1")
						.url("/files/file1")
						.build();
				List<Justification> justifications = Arrays.asList(justification);
				Statut statut = Statut.builder()
						.typeStatut(TypeStatut.SOUMISE)
						.dateHeure(LocalDateTime.now())
						.build();
				List<Statut> listeStatut = Arrays.asList(statut);
				DemandeRepriseExamen dre = DemandeRepriseExamen.builder()
						.absenceDateDebut(LocalDate.of(2022,2,2))
						.absenceDateFin(LocalDate.of(2022,2,10))
						.detenteur(etudiant)
						.listeJustification(justifications)
						.motifAbsence(MotifAbsence.MEDICAL)
						.listeStatut(listeStatut)
						.absenceDetails("Intervention chirurgicale programmée")
						.build();
				DemandeRepriseExamenRepository.save(dre);
			});
		};
	}

}
