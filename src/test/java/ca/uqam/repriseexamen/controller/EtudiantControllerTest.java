package ca.uqam.repriseexamen.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EtudiantControllerTest {

    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurDeuxAvecStatutOk() throws Exception {
        this.mockMvc.perform(get("/api/etudiants/3/historique?type=personnel").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].statut", is("SOUMISE")))
                .andExpect(jsonPath("$[0].coursGroupe", is("INF1120-030")))
                .andExpect(jsonPath("$[0].motifAbsence", is("MEDICAL")))
                .andExpect(jsonPath("$[1].statut", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[1].coursGroupe", is("INF3173-040")))
                .andExpect(jsonPath("$[1].motifAbsence", is("AUTRE")));
    }

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurUneAvecStatutOk() throws Exception {
        this.mockMvc.perform(get("/api/etudiants/4/historique?type=personnel").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void devraitRetournerStatutMauvaiseRequete()
            throws Exception {

        this.mockMvc.perform(get("/api/etudiants/1/historique").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}