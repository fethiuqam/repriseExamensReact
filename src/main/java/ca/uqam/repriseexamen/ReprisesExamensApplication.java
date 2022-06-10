package ca.uqam.repriseexamen;

import ca.uqam.repriseexamen.dao.CoursGroupeRepository;
import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.EnseignantRepository;
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
                                          EtudiantRepository etudiantRepository,
                                          EnseignantRepository enseignantRepository,
                                          CoursGroupeRepository coursGroupeRepository) {
        return args -> {
            Etudiant etudiant1 = Etudiant.builder()
                    .nom("Marshall")
                    .prenom("Marc")
                    .codePermanent("AAAA11111111")
                    .email("marshall.marc@courrier.uqam.ca")
                    .telephone("5141234567")
                    .build();
            Etudiant etudiant2 = Etudiant.builder()
                    .nom("Morrison")
                    .prenom("Jack")
                    .codePermanent("BBBB22222222")
                    .email("morrison.jack@courrier.uqam.ca")
                    .telephone("4283659874")
                    .build();
            etudiantRepository.save(etudiant1);
            etudiantRepository.save(etudiant2);
            Enseignant enseignant= Enseignant.builder()
                    .matricule("CCCC12332145")
                    .nom("Melanie")
                    .prenom("Lord")
                    .email("melanie.lord@courrier.uqam.ca")
                    .build();
            enseignantRepository.save(enseignant);
            CoursGroupe coursGroupe1 = CoursGroupe.builder()
                    .cours(Cours.builder().nom("Programmation I").sigle("INF1120").build())
                    .enseignant(enseignant)
                    .groupe("030")
                    .session(Session.HIVER)
                    .build();
            coursGroupeRepository.save(coursGroupe1);
            List<Justification> justifications1 = Arrays.asList(Justification.builder()
                            .description("justificatif 1.1")
                            .url("/files/file1.1")
                            .build(),
                    Justification.builder()
                            .description("justificatif 1.2")
                            .url("/files/file1.2")
                            .build());
            List<Statut> listeStatut1 = Arrays.asList(Statut.builder()
                    .typeStatut(TypeStatut.SOUMISE)
                    .dateHeure(LocalDateTime.of(2022, 2, 1, 8, 22, 23))
                    .build(),Statut.builder()
                    .typeStatut(TypeStatut.ENREGISTREE)
                    .dateHeure(LocalDateTime.of(2022, 1, 28, 13, 11, 5))
                    .build());
            DemandeRepriseExamen dre1 = DemandeRepriseExamen.builder()
                    .absenceDateDebut(LocalDate.of(2022, 2, 28))
                    .absenceDateFin(LocalDate.of(2022, 3, 10))
                    .etudiant(etudiant1)
                    .listeJustification(justifications1)
                    .motifAbsence(MotifAbsence.MEDICAL)
                    .listeStatut(listeStatut1)
                    .absenceDetails("Intervention chirurgicale programmée")
                    .coursGroupe(coursGroupe1)
                    .descriptionExamen("examen intra")
                    .build();
            DemandeRepriseExamenRepository.save(dre1);
            CoursGroupe coursGroupe2 = CoursGroupe.builder()
                    .cours(Cours.builder().nom("Programmation II").sigle("INF2120").build())
                    .enseignant(enseignant)
                    .groupe("020")
                    .session(Session.AUTOMNE)
                    .build();
            coursGroupeRepository.save(coursGroupe2);
            List<Justification> justifications2 = Arrays.asList(Justification.builder()
                            .description("justificatif 2.1")
                            .url("/files/file2.1")
                            .build(),
                    Justification.builder()
                            .description("justificatif 2.2")
                            .url("/files/file2.2")
                            .build());
            List<Statut> listeStatut2 = Arrays.asList(Statut.builder()
                    .typeStatut(TypeStatut.ENREGISTREE)
                    .dateHeure(LocalDateTime.of(2021, 10, 15, 23, 14, 3))
                    .build());
            DemandeRepriseExamen dre2 = DemandeRepriseExamen.builder()
                    .absenceDateDebut(LocalDate.of(2021, 12, 15))
                    .absenceDateFin(LocalDate.of(2021, 12, 16))
                    .etudiant(etudiant2)
                    .listeJustification(justifications2)
                    .motifAbsence(MotifAbsence.JUDICIAIRE)
                    .listeStatut(listeStatut2)
                    .absenceDetails("Convocation pour temoignage")
                    .coursGroupe(coursGroupe2)
                    .descriptionExamen("examen final")
                    .build();
            DemandeRepriseExamenRepository.save(dre2);
        };
    }

}
