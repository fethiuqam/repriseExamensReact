package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeStatut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RepriseControllerTest {

    private static final long SLEEP_TIME = 10;
    private static final String JSON_REPRISE = "{\"idCoursGroupe\" : 1," +
            "\"local\": \"pk4011\"," +
            "\"dureeMinutes\" : 120," +
            "\"surveillant\": \"olivier pelletier\"," +
            "\"dateHeure\": \"2023-08-01T06:08\"}";

    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private DemandeRepriseExamenRepository demandeRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitCreerUneReprisePourLeCoursGroupe1EtMettreAJourLeStatutDemandeEnPlanifiee() throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(8L);
        assertThat(demandeAvant.get().getStatutCourant()).isEqualTo(TypeStatut.ACCEPTEE);
        assertThat(demandeAvant.get().getCoursGroupe().getId()).isEqualTo(1);
        assertThat(demandeAvant.get().getCoursGroupe().getReprise()).isNull();

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_REPRISE))
                .andExpect(status().isCreated());

        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(8L);
        assertThat(demandeApres.get().getStatutCourant()).isEqualTo(TypeStatut.PLANIFIEE);
        assertThat(demandeApres.get().getCoursGroupe().getReprise()).isNotNull();
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDateHeure())
                .isEqualTo("2023-08-01T06:08");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDureeMinutes()).isEqualTo(120);
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getLocal()).isEqualTo("pk4011");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getSurveillant()).isEqualTo("olivier pelletier");
    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitModifierUneReprisePourLeCoursGroupe2() throws Exception {

        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(11L);
        assertThat(demandeApres.get().getStatutCourant()).isEqualTo(TypeStatut.PLANIFIEE);
        assertThat(demandeApres.get().getCoursGroupe().getReprise()).isNotNull();
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDateHeure())
                .isEqualTo("2022-04-15T09:30");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDureeMinutes()).isEqualTo(120);
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getLocal()).isEqualTo("PK4130");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getSurveillant()).isEqualTo("marc pelletier");

        long idReprise = demandeApres.get().getCoursGroupe().getReprise().getId();
        this.mockMvc.perform(put("/api/reprises/" + idReprise)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 2," +
                                "\"local\": \"pk4033\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"marc pelletier\"," +
                                "\"dateHeure\": \"2022-08-01T08:30\"}"))
                .andExpect(status().isOk());

        Optional<DemandeRepriseExamen> demandeModifiee = demandeRepository.findDemandeRepriseExamenById(11L);
        assertThat(demandeModifiee.get().getStatutCourant()).isEqualTo(TypeStatut.PLANIFIEE);
        assertThat(demandeModifiee.get().getCoursGroupe().getReprise()).isNotNull();
        assertThat(demandeModifiee.get().getCoursGroupe().getReprise().getDateHeure())
                .isEqualTo("2022-08-01T08:30");
        assertThat(demandeModifiee.get().getCoursGroupe().getReprise().getLocal()).isEqualTo("pk4033");
    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitSupprimerUneReprisePourLeCoursGroupe1EtMettreAJourLeStatutDemandeEnAcceptee() throws Exception {
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(11L);
        assertThat(demandeApres.get().getStatutCourant()).isEqualTo(TypeStatut.PLANIFIEE);
        assertThat(demandeApres.get().getCoursGroupe().getReprise()).isNotNull();
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDateHeure())
                .isEqualTo("2022-04-15T09:30");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getDureeMinutes()).isEqualTo(120);
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getLocal()).isEqualTo("PK4130");
        assertThat(demandeApres.get().getCoursGroupe().getReprise().getSurveillant()).isEqualTo("marc pelletier");

        long idReprise = demandeApres.get().getCoursGroupe().getReprise().getId();
        this.mockMvc.perform(delete("/api/reprises/" + idReprise)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<DemandeRepriseExamen> demandeNonPlanifiee = demandeRepository.findDemandeRepriseExamenById(11L);
        assertThat(demandeNonPlanifiee.get().getStatutCourant()).isEqualTo(TypeStatut.ACCEPTEE);
        assertThat(demandeNonPlanifiee.get().getCoursGroupe().getReprise()).isNull();
    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitRetournerStatutNonTrouvePourCoursGroupeNonExistant() throws Exception {

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 10," +
                                "\"local\": \"pk4011\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"olivier pelletier\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitRetournerStatutNonTrouvePourModificationDeRepriseNonExistante() throws Exception {

        this.mockMvc.perform(put("/api/reprises/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_REPRISE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "enseignant1")
    public void DevraitRetournerStatutNonAutorisePourEnseignant() throws Exception {

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON_REPRISE))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitRetournerStatutMauvaiseRequetePourManqueAttributDansLeCorpsDeRequette() throws Exception {

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 1," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"olivier pelletier\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 1," +
                                "\"local\": \"pk4011\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"local\": \"pk4011\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"olivier pelletier\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitRetournerStatutMauvaiseRequetePourSurveillantOuLocalVide() throws Exception {

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 1," +
                                "\"local\": \"\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"olivier pelletier\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 1," +
                                "\"local\": \"pk4011\"," +
                                "\"dureeMinutes\" : 120," +
                                "\"surveillant\": \"\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "commis")
    public void DevraitRetournerStatutMauvaiseRequetePourDureeMinutesNegative() throws Exception {

        this.mockMvc.perform(post("/api/reprises/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idCoursGroupe\" : 1," +
                                "\"local\": \"pk4011\"," +
                                "\"dureeMinutes\" : -120," +
                                "\"surveillant\": \"olivier pelletier\"," +
                                "\"dateHeure\": \"2023-08-01T06:08\"}"))
                .andExpect(status().isBadRequest());

    }
}