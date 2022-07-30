package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dao.DemandeRepriseExamenRepository;
import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.DemandeRepriseExamen;
import ca.uqam.repriseexamen.model.TypeStatut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EtudiantServiceImplTest {

    @Autowired
    private EtudiantService etudiantService;

    @MockBean
    private DemandeRepriseExamenRepository demandeRepository;

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
        when(ligneHistoriqueEtudiant1Enregistree.getStatutCourant()).thenReturn(TypeStatut.ENREGISTREE);
        when(ligneHistoriqueEtudiant1Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneHistoriqueEtudiant1Soumise.getDateHeureSoumission()).thenReturn(LocalDateTime.of(2022,2,1,10,15));
        when(ligneHistoriqueEtudiant2Soumise.getStatutCourant()).thenReturn(TypeStatut.SOUMISE);
        when(ligneHistoriqueEtudiant2Soumise.getDateHeureSoumission()).thenReturn(LocalDateTime.of(2021,2,1,10,15));


        when(demandeRepository.findLigneHistoriqueEtudiantDTOByEtudiantId(1L))
                .thenReturn(Arrays.asList(ligneHistoriqueEtudiant1Enregistree, ligneHistoriqueEtudiant1Soumise));
        when(demandeRepository.findLigneHistoriqueEtudiantDTOByEtudiantId(2L))
                .thenReturn(List.of(ligneHistoriqueEtudiant2Soumise));

        when(demandeRepository.save(any(DemandeRepriseExamen.class))).thenReturn(nouvelleDemandeRepriseExamen);
    }

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurUn() {
        List<LigneHistoriqueEtudiantDTO> result = etudiantService.getHistoriqueEtudiant(1L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void devraitRetournerListeLigneHistoriqueEtudiantDTODeLongueurUne() {
        List<LigneHistoriqueEtudiantDTO> result = etudiantService.getHistoriqueEtudiant(2L);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

}