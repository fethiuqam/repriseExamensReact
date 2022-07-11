package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDRECommisDTO;
import ca.uqam.repriseexamen.dto.LigneDREDTO;
import ca.uqam.repriseexamen.dto.LigneDREEnseignantDTO;
import ca.uqam.repriseexamen.dto.LigneDREEtudiantDTO;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
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
    @Mock
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant1Enregistree;
    @Mock
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant1Soumise;
    @Mock
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant2Soumise;
    @Mock
    private DemandeRepriseExamen nouvelleDemandeRepriseExamen;

    @Before
    public void setUp() {
        when(ligneDRECommisEnregistree.getStatut()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDRECommisSoumise.getStatut()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDRECommisAcceptee.getStatut()).thenReturn(TypeStatut.ACCEPTEE);

        when(repository.findLigneDRECommisDTOBy())
                .thenReturn(Arrays.asList(ligneDRECommisEnregistree, ligneDRECommisSoumise, ligneDRECommisAcceptee));

        when(ligneDREEnseignantEnregistree.getStatut()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEnseignantAcceptee.getStatut()).thenReturn(TypeStatut.ACCEPTEE);
        when(ligneDREEnseignantAcceptee.getDecision()).thenReturn(TypeDecision.ACCEPTEE_ENSEIGNANT);
        when(ligneDREEnseignantSoumise.getStatut()).thenReturn(TypeStatut.SOUMISE);


        when(repository.findLigneDREEnseignantDTOByCoursGroupeEnseignantId(1L))
                .thenReturn(Arrays.asList(ligneDREEnseignantEnregistree, ligneDREEnseignantAcceptee));

        when(repository.findLigneDREEnseignantDTOByCoursGroupeEnseignantId(2L))
                .thenReturn(List.of(ligneDREEnseignantSoumise));

        when(ligneDREEtudiant1Enregistree.getStatut()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneDREEtudiant1Soumise.getStatut()).thenReturn(TypeStatut.SOUMISE);
        when(ligneDREEtudiant2Soumise.getStatut()).thenReturn(TypeStatut.SOUMISE);

        when(repository.findLigneDREEtudiantDTOByEtudiantId(1L))
                .thenReturn(Arrays.asList(ligneDREEtudiant1Enregistree, ligneDREEtudiant1Soumise));

        when(repository.findLigneDREEtudiantDTOByEtudiantId(2L))
                .thenReturn(List.of(ligneDREEtudiant2Soumise));

        when(ligneHistoriqueEtudiant1Enregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneHistoriqueEtudiant1Enregistree.getEtudiantId()).thenReturn(1L);
        when(ligneHistoriqueEtudiant1Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneHistoriqueEtudiant1Soumise.getEtudiantId()).thenReturn(1L);
        when(ligneHistoriqueEtudiant2Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneHistoriqueEtudiant2Soumise.getEtudiantId()).thenReturn(2L);

        when(repository.findLigneHistoriqueEtudiantDTOBy())
                .thenReturn(Arrays.asList(
                        ligneHistoriqueEtudiant1Enregistree,
                        ligneHistoriqueEtudiant1Soumise,
                        ligneHistoriqueEtudiant2Soumise
                ));

        when(repository.save(any(DemandeRepriseExamen.class))).thenReturn(nouvelleDemandeRepriseExamen);
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

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurDeux() {
        List<LigneHistoriqueEtudiantDTO> result = service.getHistoriqueEtudiant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurUne() {
        List<LigneHistoriqueEtudiantDTO> result = service.getHistoriqueEtudiant(2L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitSoumettreNouvelleDRE() {
        DemandeRepriseExamen demandeCreee = service.soumettreDemandeRepriseExamen(nouvelleDemandeRepriseExamen);
        assertThat(demandeCreee)
                .isNotNull()
                .isEqualTo(nouvelleDemandeRepriseExamen);

    }
}