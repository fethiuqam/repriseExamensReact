package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneDREPersonnelDTO;
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
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant1Enregistree;
    @Mock
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant1Soumise;
    @Mock
    private LigneHistoriqueEtudiantDTO ligneHistoriqueEtudiant2Soumise;
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
    public void devraitRetournerListeDREPersonnelDTODeLongueurDeux() {
        List<LigneDREDTO> result = service.getAllDemandeRepriseExamenPersonnel();
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
