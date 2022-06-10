package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandeRepriseExamenServiceImplUnitTest {

    @Autowired
    private DemandeRepriseExamenService service;

    @MockBean
    private DemandeRepriseExamenRepository repository;

    private List<DemandeRepriseExamen> listeDRE;

    @Before
    public void setUp() {
        this.listeDRE = new ArrayList<>();
        List<Etudiant> etudiants = new ArrayList<>();
        Stream.of("Marc", "Richard", "Jean").forEach(nom -> {
            Etudiant etudiant = Etudiant.builder()
                    .nom(nom)
                    .codePermanent("ABCD12345678")
                    .email(nom + "@courrier.uqam.ca")
                    .telephone("1111111111")
                    .build();
            etudiants.add(etudiant);
        });
        etudiants.forEach(etudiant -> {
            Justification justification = Justification.builder()
                    .description("justificatif 1")
                    .url("/files/file1")
                    .build();
            List<Justification> justifications = Arrays.asList(justification);
            Statut statut = Statut.builder()
                    .typeStatut(TypeStatut.SOUMISE)
                    .dateHeure(LocalDateTime.now())
                    .build();
            List<Statut> listeStatute = Arrays.asList(statut);
            DemandeRepriseExamen examRR = DemandeRepriseExamen.builder()
                    .absenceDateDebut(LocalDate.of(2022, 2, 2))
                    .absenceDateFin(LocalDate.of(2022, 2, 10))
                    .etudiant(etudiant)
                    .listeJustification(justifications)
                    .motifAbsence(MotifAbsence.MEDICAL)
                    .listeStatut(listeStatute)
                    .absenceDetails("Intervention chirurgicale programmée")
                    .build();
            this.listeDRE.add(examRR);
        });
    }

    @Test
    public void devraitRetournerListeDRE() {
        when(repository.findAll()).thenReturn(this.listeDRE);
        List<DemandeRepriseExamen> result = service.getAllDemandeRepriseExamen();
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .allMatch(dre -> dre.getMotifAbsence().equals(MotifAbsence.MEDICAL));
    }

}