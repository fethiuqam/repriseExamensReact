package ca.uqam.repriseexamen.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
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

        // Requete simplifiee d'une nouvelle demande en format JSON
        String requeteDemande =
                "{\"absenceDateDebut\": \"2022-06-06\"," +
                "\"absenceDateFin\": \"2022-06-08\"," +
                "\"motifAbsence\": 1," +
                "\"absenceDetails\": \"Décès de ma grand-mère.\"}";

        this.mockMvc.perform(post("/api/demandes").contentType(MediaType.APPLICATION_JSON)
                .content(requeteDemande))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.listeStatut[0].typeStatut", is("SOUMISE")));
    }
}
