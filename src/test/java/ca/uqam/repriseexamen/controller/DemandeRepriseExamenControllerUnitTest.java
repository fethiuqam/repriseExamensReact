package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemandeRepriseExamenControllerUnitTest {
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @MockBean
    private DemandeRepriseExamenService service;

    @Mock
    private LigneDRECommisDTO ligneDRECommis1;
    @Mock
    private LigneDRECommisDTO ligneDRECommis2;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void devraitRetournerListeDREEtudiantAvecStatutOk()
            throws Exception {
        List<LigneDREEtudiantDTO> listeDREEtudiantDTO = genererListeDREEtudiantDTOPourTest();

        when(service.getAllDemandeRepriseExamenEtudiant(1L)).thenReturn(listeDREEtudiantDTO);
        
        this.mockMvc.perform(get("/api/etudiants/1/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].dateHeureSoumission", is(listeDREEtudiantDTO.get(0)
                        .getDateHeureSoumission().toString())))
                .andExpect(jsonPath("$[0].sigleCours", is(listeDREEtudiantDTO.get(0).getSigleCours())))
                .andExpect(jsonPath("$[1].dateHeureSoumission", is(listeDREEtudiantDTO.get(1)
                        .getDateHeureSoumission().toString())))
                .andExpect(jsonPath("$[1].sigleCours", is(listeDREEtudiantDTO.get(1).getSigleCours())));
    }

    @Test
    public void devraitRetournerStatutNonTrouve()
            throws Exception {
        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private List<LigneDREEtudiantDTO> genererListeDREEtudiantDTOPourTest() {
        return Arrays.asList(
                LigneDREEtudiantDTO.builder()
                        .id(1L)
                        .dateHeureSoumission(LocalDateTime.of(2022, 2, 1, 8, 22, 23))
                        .statutCourant(TypeStatut.SOUMISE)
                        .nomEnseignant("Lord Melanie")
                        .matriculeEnseignant("CCCC12345678")
                        .sigleCours("INF1120")
                        .groupe("030")
                        .session(Session.HIVER)
                        .build(),
                LigneDREEtudiantDTO.builder()
                        .id(2L)
                        .dateHeureSoumission(LocalDateTime.of(2021, 1, 31, 8, 22, 23))
                        .statutCourant(TypeStatut.ACCEPTEE)
                        .nomEnseignant("Lord Melanie")
                        .matriculeEnseignant("CCCC12345678")
                        .sigleCours("INF2120")
                        .groupe("030")
                        .session(Session.AUTOMNE)
                        .build());
    }
}