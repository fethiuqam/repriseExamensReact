package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.EtudiantRepository;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemandeRepriseExamenControllerIntegrationTest {


    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private DemandeRepriseExamenRepository DemandeRepriseExamenRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
            DemandeRepriseExamen examRR = DemandeRepriseExamen.builder()
                    .absenceDateDebut(LocalDate.of(2022, 2, 2))
                    .absenceDateFin(LocalDate.of(2022, 2, 10))
                    .detenteur(etudiant)
                    .listeJustification(justifications)
                    .motifAbsence(MotifAbsence.MEDICAL)
                    .listeStatut(listeStatut)
                    .absenceDetails("Intervention chirurgicale programmée")
                    .build();
            DemandeRepriseExamenRepository.save(examRR);
        });
    }

    @Test
    public void devraitRetournerListeDREavecStatutOk()
            throws Exception {

        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[0].motifAbsence", is("MEDICAL")))
                .andExpect(jsonPath("$[0].detenteur.nom", is("Marc")))
                .andExpect(jsonPath("$[1].detenteur.nom", is("Richard")))
                .andExpect(jsonPath("$[2].detenteur.nom", is("Jean")));
    }

    @Test
    public void devraitRetournerStatutNonTrouve()
            throws Exception {

        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
