package ca.uqam.repriseexamen.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.service.DemandeRepriseExamenService;

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
    public void devraitRetournerStatutNonTrouve()
            throws Exception {
        this.mockMvc.perform(get("/api/demande").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}