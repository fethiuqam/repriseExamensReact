package ca.uqam.repriseexamen.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AuthentificationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // tests commis
   // @Test
//    public void testConnexionCommisRetourneLesInformationsAvecStatutOk() throws Exception {
//        this.mockMvc
//                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"codeMs\": \"commis\", \"motDePasse\": \"1\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", aMapWithSize(3)))
//                .andExpect(jsonPath("$.id", is(notNullValue())))
//                .andExpect(jsonPath("$.type", is("personnel")))
//                .andExpect(jsonPath("$.permissions", is(notNullValue())))
//                .andExpect(cookie().value("token", is(notNullValue())));
//    }


    @Test
    public void testConnexionCommisMotDePasseEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"commis\", \"motDePasse\": \"2\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testConnexionCodeMsEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"utilisateursCodeMsErrone\", \"motDePasse\": \"1\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "commis", password = "1")
    @Test
    public void testDeconnexionCommisAvecStatutOkEtTokenVide() throws Exception {
        this.mockMvc
                .perform(post("/api/logout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", is("")));
    }

    // tests etudiant1
    @Test
    public void testConnexionEtudiant1RetourneLesInformationsAvecStatutOk() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"etudiant1\", \"motDePasse\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.type", is("etudiant")))
                .andExpect(cookie().value("token", is(notNullValue())));
    }

    @Test
    public void testConnexionEtudiant1MotDePasseEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"etudiant1\", \"motDePasse\": \"2\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "etudiant1", password = "1")
    @Test
    public void testDeconnexionEtudiant1AvecStatutOkEtTokenVide() throws Exception {
        this.mockMvc
                .perform(post("/api/logout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", is("")));
    }

    // tests etudiant2
    @Test
    public void testConnexionEtudiant2RetourneLesInformationsAvecStatutOk() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"etudiant2\", \"motDePasse\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.type", is("etudiant")))
                .andExpect(cookie().value("token", is(notNullValue())));
    }

    @Test
    public void testConnexionEtudiant2MotDePasseEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"etudiant2\", \"motDePasse\": \"2\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "etudiant2", password = "2")
    @Test
    public void testDeconnexionEtudiant2AvecStatutOkEtTokenVide() throws Exception {
        this.mockMvc
                .perform(post("/api/logout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", is("")));
    }

    // tests enseignant1
    @Test
    public void testConnexionEnseignant1RetourneLesInformationsAvecStatutOk() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"enseignant1\", \"motDePasse\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.type", is("enseignant")))
                .andExpect(cookie().value("token", is(notNullValue())));
    }

    @Test
    public void testConnexionEenseignant1MotDePasseEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"enseignant1\", \"motDePasse\": \"2\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "enseignant1", password = "1")
    @Test
    public void testDeconnexionEnseignant1AvecStatutOkEtTokenVide() throws Exception {
        this.mockMvc
                .perform(post("/api/logout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", is("")));
    }

    // tests enseignant2
    @Test
    public void testConnexionEnseignant2RetourneLesInformationsAvecStatutOk() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"enseignant2\", \"motDePasse\": \"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.type", is("enseignant")))
                .andExpect(cookie().value("token", is(notNullValue())));
    }

    @Test
    public void testConnexionEnseignant2MotDePasseEronneAccesNonAutorise() throws Exception {
        this.mockMvc
                .perform(post("/api/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codeMs\": \"enseignant2\", \"motDePasse\": \"2\"}"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "enseignant2", password = "1")
    @Test
    public void testDeconnexionEnseignant2AvecStatutOkEtTokenVide() throws Exception {
        this.mockMvc
                .perform(post("/api/logout").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().value("token", is("")));
    }
}
