package ca.uqam.repriseexamen.controller;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("datatest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CoursGroupeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext context;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @WithMockUser(username="commis")
    @Test
    public void devraitRetournerUneListeDeLongueur1DesCoursGroupesAPlanifier() throws Exception {
        this.mockMvc.perform(get("/api/coursGroupes/planification").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cours.sigle", is("INF1120")))
                .andExpect(jsonPath("$[0].reprise", nullValue()))
                .andExpect(jsonPath("$[0].groupe", is("030")))
                .andExpect(jsonPath("$[1].cours.sigle", is("INF2120")))
                .andExpect(jsonPath("$[1].reprise", notNullValue()))
                .andExpect(jsonPath("$[1].groupe", is("020")));
    }

}