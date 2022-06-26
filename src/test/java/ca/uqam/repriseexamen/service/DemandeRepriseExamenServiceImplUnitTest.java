package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandeRepriseExamenServiceImplUnitTest {

    @Autowired
    private DemandeRepriseExamenService service;
    @MockBean
    private DemandeRepriseExamenRepository repository;
    private List<DemandeRepriseExamen> listeDRE;
    private List<LigneDRECommisDTO> listeLigneDTO;

    @Mock
    private LigneDRECommisDTO ligneDRE1;
    @Mock
    private LigneDRECommisDTO ligneDRE2;

    @Before
    public void setUp() {
        Etudiant etudiant1 = Etudiant.builder()
                .id(1L)
                .nom("Marshall")
                .prenom("Marc")
                .codePermanent("AAAA11111111")
                .email("marshall.marc@courrier.uqam.ca")
                .telephone("5141234567")
                .build();

        Etudiant etudiant2 = Etudiant.builder()
                .id(2L)
                .nom("Morrison")
                .prenom("Jack")
                .codePermanent("BBBB22222222")
                .email("morrison.jack@courrier.uqam.ca")
                .telephone("4283659874")
                .build();

        Enseignant enseignant = Enseignant.builder()
                .matricule("CCCC12332145")
                .nom("Melanie")
                .prenom("Lord")
                .email("melanie.lord@courrier.uqam.ca")
                .build();

        CoursGroupe coursGroupe1 = CoursGroupe.builder()
                .cours(Cours.builder().nom("Programmation I").sigle("INF1120").build())
                .enseignant(enseignant)
                .groupe("030")
                .session(Session.HIVER)
                .build();

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
                .build(),
                Statut.builder()
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

        CoursGroupe coursGroupe2 = CoursGroupe.builder()
                .cours(Cours.builder().nom("Programmation II").sigle("INF2120").build())
                .enseignant(enseignant)
                .groupe("020")
                .session(Session.AUTOMNE)
                .build();

        List<Justification> justifications2 = Arrays.asList(Justification.builder()
                .description("justificatif 2.1")
                .url("/files/file2.1")
                .build(),
                Justification.builder()
                        .description("justificatif 2.2")
                        .url("/files/file2.2")
                        .build());

        List<Statut> listeStatut2 = List.of(Statut.builder()
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

        this.listeDRE = Arrays.asList(dre1, dre2);
    }

    @Test
    public void devraitRetournerListeDRECommisDTO() {
        when(ligneDRE1.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDRE2.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);

        when(repository.findLigneDRECommisDTOBy()).thenReturn(Arrays.asList(ligneDRE1, ligneDRE2));
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenCommis();
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTO() {
        when(repository.findDemandeRepriseExamenBy()).thenReturn(this.listeDRE);
        List<LigneDREEtudiantDTO> result = service.getAllDemandeRepriseExamenEtudiant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

}