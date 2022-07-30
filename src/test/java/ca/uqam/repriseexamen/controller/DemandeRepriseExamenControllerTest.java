package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.model.*;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DemandeRepriseExamenControllerTest {

    private static final long SLEEP_TIME = 10;
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    private DemandeRepriseExamenRepository demandeRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerListeDREPersonnelDTODeLongueurDeuxAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$[0].*", hasSize(16)))
                .andExpect(jsonPath("$[0].statutCourant", is("ANNULEE")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF1120")))
                .andExpect(jsonPath("$[1].statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[1].coursGroupe.cours.sigle", is("INF3173")));
    }


    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerListeDREEnseignantDTODeLongueurUneAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF3173")));
    }

    @WithMockUser(username = "enseignant2")
    @Test
    public void devraitRetournerListeDREEnseignantDTOVideAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].statutCourant", is("REJETEE")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF2120")))
                .andExpect(jsonPath("$[1].statutCourant", is("PLANIFIEE")))
                .andExpect(jsonPath("$[1].coursGroupe.cours.sigle", is("INF2120")));
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurDeuxAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].*", hasSize(14)))
                .andExpect(jsonPath("$[0].statutCourant", is("ANNULEE")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF1120")))
                .andExpect(jsonPath("$[1].statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[1].coursGroupe.cours.sigle", is("INF3173")));
    }

    @WithMockUser(username = "etudiant2")
    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurUneAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].*", hasSize(14)))
                .andExpect(jsonPath("$[0].statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF3173")));

    }

    @WithMockUser(username = "etudiant3")
    @Test
    public void devraitRetournerListeDREEtudiantDTOVideAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].statutCourant", is("REJETEE")))
                .andExpect(jsonPath("$[0].coursGroupe.cours.sigle", is("INF2120")));
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerDREPersonnelDTODuPremierEtudiantAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(16)))
                .andExpect(jsonPath("$.statutCourant", is("ANNULEE")))
                .andExpect(jsonPath("$.coursGroupe.cours.sigle", is("INF1120")));
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerDREPersonnelDTODuDeuxiemeEtudiantAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(16)))
                .andExpect(jsonPath("$.statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$.coursGroupe.cours.sigle", is("INF3173")));
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNonTrouvePourIdNonValide()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/20").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerDREEtudiantDTODuPremierEtudiantAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(14)))
                .andExpect(jsonPath("$.statutCourant", is("ANNULEE")))
                .andExpect(jsonPath("$.coursGroupe.cours.sigle", is("INF1120")));
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerStatutNonAutorisePourDREDuDeuxiemeEtudiant()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "etudiant2")
    @Test
    public void devraitRetournerDREEtudiantDTODuDeuxiemeEtudiantAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(14)))
                .andExpect(jsonPath("$.statutCourant", is("EN_TRAITEMENT")))
                .andExpect(jsonPath("$.coursGroupe.cours.sigle", is("INF3173")));
    }

    @WithMockUser(username = "etudiant2")
    @Test
    public void devraitRetournerStatutNonAutorisePourDREDupremierEtudiant()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerDREEnseignantDTODuPremierEtudiantAvecStatutOk()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(11)))
                .andExpect(jsonPath("$.statutCourant", is("ANNULEE")))
                .andExpect(jsonPath("$.coursGroupe.cours.sigle", is("INF1120")));
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerAuPremierEnseignantStatutNonAutorisePourDREDuDeuxiemeEtudiant()
            throws Exception {
        this.mockMvc.perform(get("/api/demandes/4").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void devraitRetournerNouvelleDemandeSoumiseAvecStatutOk()
            throws Exception {

        DemandeRepriseExamen demande = DemandeRepriseExamen.builder()
                .absenceDateDebut(LocalDate.of(2022, 6, 6))
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
                .andExpect(jsonPath("$.listeStatut[0].typeStatut", is("SOUMISE")));
    }

    @Test
    public void devraitRetournerStatutNoContentPourAnnulerDemandeEtudiant()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/annuler-etudiant")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.ANNULEE);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoContentPourAccepterCommisBodyVide()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/9/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(9L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
        assertThat(demande.get().getDecisionCourante().getDetails()).isNull();
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutBadRequestPourRejeterCommisBodyVide()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/9/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutBadRequestPourRejeterCommisDetailsLongueurDeux()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"xx\"}"))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoContentPourAccepterCommisBodyAvecDetails()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"details de decision\"}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull().isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
        assertThat(demande.get().getDecisionCourante().getDetails()).isEqualTo("details de decision");
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerNotAcceptableStatutPourAccepterCommisDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull().isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoContentPourRejeterCommisBodyVide()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull().isEqualTo(TypeDecision.REJET_RECOMMANDE);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerNotAcceptableStatutPourRejeterCommisDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterCommisDejaRejeteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJET_RECOMMANDE);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoContentPourAccepterDirecteurNonAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/9/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(9L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoContentPourAccepterDirecteurDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision())
                .isNotNull().isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoContentPourRejeterDirecteurDejaAccepteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision())
                .isNotNull().isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoContentPourRejeterDirecteurDejaRejeteeCommis()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/4/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(4L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision())
                .isNotNull().isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourAccepterDirecteurDejaAccepteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        this.mockMvc.perform(patch("/api/demandes/2/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterDirecteurDejaAccepteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/2/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        this.mockMvc.perform(patch("/api/demandes/2/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejeterDirecteurDejaRejeteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNoContentPourAccepterEnseignantDejaAccepterDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/3/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(3L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_ENSEIGNANT);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.ACCEPTEE);
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNoContentPourRejeterEnseignantDejaAccepterDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/3/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(3L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_ENSEIGNANT);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }


    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourAccepterEnseignantDejaRejeteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/6/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(6L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNoTAcceptablePourRejterEnseignantDejaRejeteeDirecteur()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/6/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(6L);
        assertThat(demande.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demande.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
    }


    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoContentPourAnnulerRejetCommisDejaRejeteeCommis()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(4L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJET_RECOMMANDE);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
        this.mockMvc.perform(patch("/api/demandes/4/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(4L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.AUCUNE);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNotAcceptablePourAnnulerRejetCommisNonRejetee()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.AUCUNE);
        this.mockMvc.perform(patch("/api/demandes/1/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.AUCUNE);
    }

    @WithMockUser(username = "commis")
    @Test
    public void devraitRetournerStatutNoContentPourAnnulerRejetCommisDejaAccepteeCommis()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
        this.mockMvc.perform(patch("/api/demandes/2/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNoContentPourAnnulerRejetDirecteurDejaRejeteeDirecteur()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(6L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_DIRECTEUR);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
        this.mockMvc.perform(patch("/api/demandes/6/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(6L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTATION_RECOMMANDEE);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "directeur")
    @Test
    public void devraitRetournerStatutNotAcceptablePourAnnulerRejetDirecteurDejaAccepteeDirecteur()
            throws Exception {

        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(3L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
        this.mockMvc.perform(patch("/api/demandes/3/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(3L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNoContentPourAnnulerRejetEnseignantDejaRejeteeEnseignant()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(7L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.REJETEE_ENSEIGNANT);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.REJETEE);
        this.mockMvc.perform(patch("/api/demandes/7/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(7L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_DIRECTEUR);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.EN_TRAITEMENT);
    }

    @WithMockUser(username = "enseignant1")
    @Test
    public void devraitRetournerStatutNotAcceptablePourAnnulerRejetEnseignantDejaAccepteeEnseignant()
            throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(5L);
        assertThat(demandeAvant.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_ENSEIGNANT);
        assertThat(demandeAvant.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.COMPLETEE);
        this.mockMvc.perform(patch("/api/demandes/5/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotAcceptable());
        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(5L);
        assertThat(demandeApres.get().getDecisionCourante().getTypeDecision()).isNotNull()
                .isEqualTo(TypeDecision.ACCEPTEE_ENSEIGNANT);
        assertThat(demandeApres.get().getStatutCourant()).isNotNull().isEqualTo(TypeStatut.COMPLETEE);
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerStatutUnauthorizedPourAccepterDemandeParUnEtudiant()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/accepter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerStatutUnauthorizedPourRejeterDemandeParUnEtudiant()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/1/rejeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\" : \"demande non conforme\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "etudiant1")
    @Test
    public void devraitRetournerStatutUnauthorizedPourAnnulerRejetDemandeParUnEtudiant()
            throws Exception {
        this.mockMvc.perform(patch("/api/demandes/4/annuler-rejet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "etudiant1")
    public void envoyerMessageDevraitRetournerTypeMessageEtudiantAvecTailleDeux() throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demandeAvant.get().getTypeMessageCourant()).isNotNull().isEqualTo(TypeMessage.DEMANDE_COMMIS);
        assertThat(demandeAvant.get().getListeMessage()).isNotNull().hasSize(1);

        this.mockMvc.perform(post("/api/demandes/1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"message test réponse etudiant\"}"))
                .andExpect(status().isNoContent());

        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(1L);
        assertThat(demandeApres.get().getTypeMessageCourant()).isNotNull().isEqualTo(TypeMessage.REPONSE_ETUDIANT);
        assertThat(demandeApres.get().getListeMessage()).isNotNull().hasSize(2);
    }

    @Test
    @WithMockUser(username = "commis")
    public void envoyerMessageDevraitRetournerTypeMessageCommisAvecTaille3() throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demandeAvant.get().getTypeMessageCourant()).isNotNull().isEqualTo(TypeMessage.REPONSE_ETUDIANT);
        assertThat(demandeAvant.get().getListeMessage()).isNotNull().hasSize(2);

        this.mockMvc.perform(post("/api/demandes/2/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"message test réponse commis\"}"))
                .andExpect(status().isNoContent());

        Optional<DemandeRepriseExamen> demandeApres = demandeRepository.findDemandeRepriseExamenById(2L);
        assertThat(demandeApres.get().getTypeMessageCourant()).isNotNull().isEqualTo(TypeMessage.DEMANDE_COMMIS);
        assertThat(demandeApres.get().getListeMessage()).isNotNull().hasSize(3);
    }

    @Test
    @WithMockUser(username = "etudiant1")
    public void envoyerMessageDevraitRetournerForbiddenPourEtudiantSiAucuneDemandeInfos() throws Exception {
        Optional<DemandeRepriseExamen> demandeAvant = demandeRepository.findDemandeRepriseExamenById(3L);
        assertThat(demandeAvant.get().getTypeMessageCourant()).isNull();
        assertThat(demandeAvant.get().getListeMessage()).isNullOrEmpty();

        this.mockMvc.perform(post("/api/demandes/3/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"message test réponse étudiant\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "commis")
    public void envoyerMessageDevraitRetournerStatutNotAcceptableSiBodyNonConforme() throws Exception {
        this.mockMvc.perform(post("/api/demandes/3/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "commis")
    public void envoyerMessageDevraitRetournerNotFoundSiDemandeExistePas() throws Exception {
        this.mockMvc.perform(post("/api/demandes/20/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "enseignant1")
    public void envoyerMessageDevraitRetournerUnauthorizedSiEnseignant() throws Exception {
        this.mockMvc.perform(post("/api/demandes/1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"test\"}"))
                .andExpect(status().isUnauthorized());
    }
}
