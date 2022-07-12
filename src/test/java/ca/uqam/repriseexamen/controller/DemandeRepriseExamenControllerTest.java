package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.MotifAbsence;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DemandeRepriseExamenControllerTest {

    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void devraitRetournerListeDRECommisDTODeLongueurDeuxAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=commis").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].statut", is("SOUMISE")))
                .andExpect(jsonPath("$[0].sigleCours", is("INF1120")))
                .andExpect(jsonPath("$[1].statut", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[1].sigleCours", is("INF3173")));
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTODeLongueurUneAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=enseignant&id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
//                .andExpect(jsonPath("$[0].statut", is("EN_TRAITEMENT")))
//                .andExpect(jsonPath("$[0].sigleCours", is("INF3173")));
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTOVideAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=enseignant&id=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurDeuxAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=etudiant&id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].statut", is("SOUMISE")))
                .andExpect(jsonPath("$[0].sigleCours", is("INF1120")))
                .andExpect(jsonPath("$[1].statut", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[1].sigleCours", is("INF3173")));
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurUneAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=etudiant&id=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].statut", is("ENREGISTREE")))
                .andExpect(jsonPath("$[0].sigleCours", is("INF2120")));

    }

    @Test
    public void devraitRetournerListeDREEtudiantDTOVideAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes?role=etudiant&id=3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void devraitRetournerStatutNonTrouve()
            throws Exception {

        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void devraitRetournerStatutMauvaiseRequete()
            throws Exception {

        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void devraitRetournerNouvelleDemandeSoumiseAvecStatutOk()
            throws Exception {

        DemandeRepriseExamen demande = DemandeRepriseExamen.builder()
                .absenceDateDebut(LocalDate.of(2022, 6,6))
                .absenceDateFin(LocalDate.of(2022, 6, 8))
                .motifAbsence(MotifAbsence.DECES)
                .absenceDetails("Deces de ma grande tante")
                .build();

        ObjectMapper objectMapper =
                new ObjectMapper().registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        MockMultipartFile dre = new MockMultipartFile("nouvelleDemande", null,
                "application/json", objectMapper.writeValueAsBytes(demande));

        MockMultipartFile fichier = new MockMultipartFile("files",
                "filename.pdf",
                "application/pdf",
                "test".getBytes());

        this.mockMvc.perform(multipart("/api/demandes").file(dre).file(fichier))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.dateSoumission", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.listeStatut[0].typeStatut", is("SOUMISE")));

    }

    @Test
    public void devraitRetournerStatutNoContentPourAccepterCommisBodyVide()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/accepter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerStatutNoContentPourAccepterCommisBodyAvecDetails()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/accepter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"details de decision\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerNotAcceptableStatutPourAccepterCommisDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoContentPourRejeterCommisBodyVide()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerNotAcceptableStatutPourRejeterCommisDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterCommisDejaRejeteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNotAcceptablePourAccepterDirecteurNonAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoContentPourAccepterDirecteurDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerStatutNotAcceptablePourRejeterDirecteurDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoContentPourRejeterDirecteurDejaRejeteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerStatutNoTAcceptablePourAccepterDirecteurDejaAccepteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterDirecteurDejaAccepteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/2/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterDirecteurDejaRejeteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void devraitRetournerStatutNoContentPourAccepterEnseignantDejaAccepterDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/2/accepter-enseignant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerStatutNoContentPourRejeterEnseignantDejaAccepterDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/2/rejeter-enseignant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void devraitRetournerStatutNoTAcceptablePourAccepterEtRejterEnseignantDejaRejeteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-commis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-directeur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(patch("/api/demandes/1/accepter-enseignant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        this.mockMvc.perform(patch("/api/demandes/1/rejeter-enseignant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
    }

}
