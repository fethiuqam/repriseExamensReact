package ca.uqam.repriseexamen.controller;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dao.RoleRepository;
import ca.uqam.repriseexamen.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleControllerTest {
    @Autowired
    protected WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DemandeRepriseExamenRepository DemandeRepriseExamenRepository;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        List<Permission> permissions = new ArrayList<>();
        List<Permission> permissions2 = new ArrayList<>();
        List<Permission> permissions3 = new ArrayList<>();
        permissions.add(Permission.AfficherDRE);
        permissions.add(Permission.ListerDRE);
        permissions2.add(Permission.AfficherDRE);
        permissions2.add(Permission.ListerDRE);
        permissions2.add(Permission.AfficherJustificatifs);
        permissions2.add(Permission.PlanifierDates);
        permissions3.add(Permission.AfficherDRE);
        permissions3.add(Permission.ListerDRE);
        permissions3.add(Permission.GererRoles);
        permissions3.add(Permission.GererUsagers);

        Role role1 = Role.builder()
                .nom("directeur")
                .permissions(permissions)
                .build();
        roleRepository.save(role1);
        Role role2 = Role.builder()
                .nom("Commis")
                .permissions(permissions2)
                .build();
        roleRepository.save(role2);
    }
    @Test
    public void getRoles() throws Exception {
        this.mockMvc.perform(get("/api/roles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roles[0].nom", is("directeur")))

                .andExpect(jsonPath("$._embedded.roles[2].nom", is("Commis")));
    }
    @Test
    public void getRoleById() throws Exception {
        this.mockMvc.perform(get("/api/roles/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("directeur")))
                .andExpect(jsonPath("$.permissions[3]", is("JugerCommis")));
    }
    @Test
    public void getRoleByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/api/roles/8").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void createRole() throws Exception {
        List<Permission> permissions4 = new ArrayList<>();
        permissions4.add(Permission.JugerEnseignant);

        this.mockMvc.perform(post("/api/roles").content(asJsonString(new Role("Enseignant", permissions4)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    public void updateRole() throws Exception {
        List<Permission> permissions5 = new ArrayList<>();
        permissions5.add(Permission.AfficherDRE);
        permissions5.add(Permission.ListerDRE);
        permissions5.add(Permission.AfficherJustificatifs);
        permissions5.add(Permission.PlanifierDates);

        this.mockMvc.perform(put("/api/roles/3")
                        .content(asJsonString(new Role("Nouveau Role", permissions5)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("Nouveau Role")));
    }
    @Test
    public void deleteRole() throws Exception {
        this.mockMvc.perform(delete("/api/roles/3"))
                .andExpect(status().isNoContent());
        this.mockMvc.perform(get("/api/roles/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
