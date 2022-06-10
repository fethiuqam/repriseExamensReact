package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.model.*;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;
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
public class DemandeRepriseExamenRestControllerUnitTest {
    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;
    @MockBean
    private DemandeRepriseExamenService service;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void devraitRetournerListeDREavecStatutOk()
            throws Exception {
        List<LigneDRECommisDTO> listeDRECommisDTO = genererListeDRECommisDTOPourTest();
        when(service.getAllDemandeRepriseExamen()).thenReturn(listeDRECommisDTO);
        this.mockMvc.perform(get("/api/demandes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].dateHeureSoumission", is(listeDRECommisDTO.get(0)
                        .getDateHeureSoumission().toString())))
                .andExpect(jsonPath("$[0].sigleCours", is(listeDRECommisDTO.get(0).getSigleCours())))
                .andExpect(jsonPath("$[1].dateHeureSoumission", is(listeDRECommisDTO.get(1)
                        .getDateHeureSoumission().toString())))
                .andExpect(jsonPath("$[1].sigleCours", is(listeDRECommisDTO.get(1).getSigleCours())));
    }

    @Test
    public void devraitRetournerStatutNonTrouve()
            throws Exception {
        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    private List<LigneDRECommisDTO> genererListeDRECommisDTOPourTest() {
        return Arrays.asList(
                LigneDRECommisDTO.builder()
                        .id(1L)
                        .dateHeureSoumission(LocalDateTime.of(2022, 2, 1, 8, 22, 23))
                        .statutCourant(TypeStatut.SOUMISE)
                        .nomEtudiant("Marc Marshall")
                        .codePermanentEtudiant("AAAA12345678")
                        .nomEnseignat("Lord Melanie")
                        .matriculeEnseignat("CCCC12345678")
                        .sigleCours("INF1120")
                        .groupe("030")
                        .session(Session.HIVER)
                        .build(),
                LigneDRECommisDTO.builder()
                        .id(2L)
                        .dateHeureSoumission(LocalDateTime.of(2021, 1, 31, 8, 22, 23))
                        .statutCourant(TypeStatut.ACCEPTEE)
                        .nomEtudiant("Jack Morisson")
                        .codePermanentEtudiant("BBBB12345678")
                        .nomEnseignat("Lord Melanie")
                        .matriculeEnseignat("CCCC12345678")
                        .sigleCours("INF2120")
                        .groupe("030")
                        .session(Session.AUTOMNE)
                        .build()
        );
    }
}