package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDREPersonnelDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeDecision;
import ca.uqam.repriseexamen.model.TypeStatut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemandeRepriseExamenServiceImplTest {

    @Autowired
    private DemandeRepriseExamenService demandeService;
    @MockBean
    private DemandeRepriseExamenRepository demandeRepository;
    @Mock
    private LigneDREPersonnelDTO ligneDREPersonnelEnregistree;
    @Mock
    private LigneDREPersonnelDTO ligneDREPersonnelSoumise;
    @Mock
    private LigneDREPersonnelDTO ligneDREPersonnelAcceptee;
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

    @Mock
    private DemandeRepriseExamen nouvelleDemandeRepriseExamen;

    @Before
    public void setUp() {
        when(ligneDREPersonnelEnregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREPersonnelSoumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREPersonnelAcceptee.getStatutCourant()).thenReturn(TypeStatut.ACCEPTEE);

        when(repository.findLigneDREPersonnelDTOBy())
                .thenReturn(Arrays.asList(
                        ligneDREPersonnelEnregistree,
                        ligneDREPersonnelSoumise,
                        ligneDREPersonnelAcceptee));

        when(ligneDREEnseignantEnregistree.getStatut()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEnseignantAcceptee.getStatut()).thenReturn(TypeStatut.ACCEPTEE);
        when(ligneDREEnseignantAcceptee.getDecision()).thenReturn(TypeDecision.ACCEPTEE_ENSEIGNANT);
        when(ligneDREEnseignantSoumise.getStatut()).thenReturn(TypeStatut.SOUMISE);


        when(demandeRepository.findLigneDREEnseignantDTOByCoursGroupeEnseignantId(1L))
                .thenReturn(Arrays.asList(ligneDREEnseignantEnregistree, ligneDREEnseignantAcceptee));

        when(demandeRepository.findLigneDREEnseignantDTOByCoursGroupeEnseignantId(2L))
                .thenReturn(List.of(ligneDREEnseignantSoumise));

        when(ligneDREEtudiant1Enregistree.getStatut()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEtudiant1Soumise.getStatut()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREEtudiant2Soumise.getStatut()).thenReturn(TypeStatut.SOUMISE);

        when(demandeRepository.findLigneDREEtudiantDTOByEtudiantId(1L))
                .thenReturn(Arrays.asList(ligneDREEtudiant1Enregistree, ligneDREEtudiant1Soumise));

        when(demandeRepository.findLigneDREEtudiantDTOByEtudiantId(2L))
                .thenReturn(List.of(ligneDREEtudiant2Soumise));

        when(demandeRepository.save(any(DemandeRepriseExamen.class))).thenReturn(nouvelleDemandeRepriseExamen);
    }

    @Test
    public void devraitRetournerListeDREPersonnelDTODeLongueurDeux() {
        List<LigneDREDTO> result = demandeService.getAllDemandeRepriseExamenPersonnel();
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTODeLongueurUne() {
        List<LigneDREDTO> result = demandeService.getAllDemandeRepriseExamenEnseignant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitRetournerListeDREEnseignantDTOVide() {
        List<LigneDREDTO> result = demandeService.getAllDemandeRepriseExamenEnseignant(2L);
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurDeux() {
        List<LigneDREDTO> result = demandeService.getAllDemandeRepriseExamenEtudiant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void devraitRetournerListeDREEtudiantDTODeLongueurUne() {
        List<LigneDREDTO> result = demandeService.getAllDemandeRepriseExamenEtudiant(2L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitSoumettreNouvelleDRE() {
        DemandeRepriseExamen demandeCreee = demandeService.soumettreDemandeRepriseExamen(nouvelleDemandeRepriseExamen);
        assertThat(demandeCreee)
                .isNotNull()
                .isEqualTo(nouvelleDemandeRepriseExamen);

    }
}