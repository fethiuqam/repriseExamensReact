package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandeRepriseExamenServiceImplTest {

    @Autowired
    private DemandeRepriseExamenService service;
    @MockBean
    private DemandeRepriseExamenRepository repository;
    @Mock
    private LigneDRECommisDTO ligneDRECommisEnregistree;
    @Mock
    private LigneDRECommisDTO ligneDRECommisSoumise;
    @Mock
    private LigneDRECommisDTO ligneDRECommisAcceptee;
    @Mock
    private LigneDREEnseignantDTO ligneDREEnseignantEnregistree;
    @Mock
    private LigneDREEnseignantDTO ligneDREEnseignantSoumise;
    @Mock
    private LigneDREEnseignantDTO ligneDREEnseignantAcceptee;
    @Mock
    private LigneDREEtudiantDTO ligneDREEtudiant1Enregistree;
    @Mock
    private LigneDREEtudiantDTO ligneDREEtudiant1Soumise;
    @Mock
    private LigneDREEtudiantDTO ligneDREEtudiant2Soumise;

    @Before
    public void setUp() {
        when(ligneDRECommisEnregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDRECommisSoumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDRECommisAcceptee.getStatutCourant()).thenReturn(TypeStatut.ACCEPTEE);

        when(repository.findLigneDRECommisDTOBy())
                .thenReturn(Arrays.asList(
                        ligneDRECommisEnregistree,
                        ligneDRECommisSoumise,
                        ligneDRECommisAcceptee));

        when(ligneDREEnseignantEnregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEnseignantEnregistree.getEnseignantId()).thenReturn(1L);
        when(ligneDREEnseignantSoumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREEnseignantSoumise.getEnseignantId()).thenReturn(2L);
        when(ligneDREEnseignantAcceptee.getStatutCourant()).thenReturn(TypeStatut.ACCEPTEE);
        when(ligneDREEnseignantAcceptee.getEnseignantId()).thenReturn(1L);

        when(repository.findLigneDREEnseignantDTOBy())
                .thenReturn(Arrays.asList(
                        ligneDREEnseignantEnregistree,
                        ligneDREEnseignantSoumise,
                        ligneDREEnseignantAcceptee));

        when(ligneDREEtudiant1Enregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEtudiant1Enregistree.getEtudiantId()).thenReturn(1L);
        when(ligneDREEtudiant1Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREEtudiant1Soumise.getEtudiantId()).thenReturn(1L);
        when(ligneDREEtudiant2Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREEtudiant2Soumise.getEtudiantId()).thenReturn(2L);

        when(repository.findLigneDREEtudiantDTOBy())
                .thenReturn(Arrays.asList(
                        ligneDREEtudiant1Enregistree,
                        ligneDREEtudiant1Soumise,
                        ligneDREEtudiant2Soumise));

    }

    @Test
    public void devraitRetournerListeDRECommisDTODeLongueurDeux() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenCommis();
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTODeLongueurUne() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenEnseignant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTOVide() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenEnseignant(2L);
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurDeux() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenEtudiant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurUne() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenEtudiant(2L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }
}