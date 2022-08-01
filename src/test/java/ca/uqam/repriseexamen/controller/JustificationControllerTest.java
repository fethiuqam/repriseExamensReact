package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.JustificationRepository;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.Justification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JustificationControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private DemandeRepriseExamenRepository demandeRepository;

    @Autowired
    private JustificationRepository justificationRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @WithMockUser(username = "etudiant2")
    @Test
    public void devraitRetournerStatutNoContentAvecAjoutNouvellePieceJustificative() throws Exception {
        MockMultipartFile dre = new MockMultipartFile("id", null,
                "application/json", "3".getBytes());

        MockMultipartFile fichier = new MockMultipartFile("file",
                "new_filename.pdf",
                "application/pdf",
                "test".getBytes());

        this.mockMvc.perform(multipart("/api/justifications").file(dre).file(fichier))
                .andExpect(status().isNoContent());

        Optional<DemandeRepriseExamen> demande = demandeRepository.findDemandeRepriseExamenById(3L);
        Justification nouvelleJustification = demande.get().getListeJustification().get(2);

        assertThat(nouvelleJustification.getDemandeRepriseExamen().getId()).isNotNull().isEqualTo(3);
    }

    @WithMockUser(username = "etudiant2")
    @Test
    public void devraitRetournerStatutNoContentAvecPieceJustificativeSupprimee() throws Exception {

        this.mockMvc.perform(delete("/api/justifications/3"))
                .andExpect(status().isNoContent());
    }
}
