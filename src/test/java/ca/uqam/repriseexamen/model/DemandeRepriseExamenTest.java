package ca.uqam.repriseexamen.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DemandeRepriseExamenTest {

    private DemandeRepriseExamen dreEnregistree;
    private DemandeRepriseExamen dreEnregistreeSoumise;
    private DemandeRepriseExamen dreEnregistreeSoumiseEnTraitement;


    @Before
    public void setUp() {
        Statut statutEnregistree = Statut.builder()
                .typeStatut(TypeStatut.ENREGISTREE)
                .dateHeure(LocalDateTime.of(2022, 2, 1, 8, 22, 23))
                .build();
        Statut statutSoumise = Statut.builder()
                .typeStatut(TypeStatut.SOUMISE)
                .dateHeure(LocalDateTime.of(2022, 2, 3, 1, 23, 23))
                .build();
        Statut statutEnTraitement = Statut.builder()
                .typeStatut(TypeStatut.EN_TRAITEMENT)
                .dateHeure(LocalDateTime.of(2022, 2, 10, 2, 5, 0))
                .build();

        this.dreEnregistree = DemandeRepriseExamen.builder()
                .listeStatut(List.of(statutEnregistree))
                .build();
        this.dreEnregistreeSoumise = DemandeRepriseExamen.builder()
                .listeStatut(Arrays.asList(statutEnregistree, statutSoumise))
                .build();
        this.dreEnregistreeSoumiseEnTraitement = DemandeRepriseExamen.builder()
                .listeStatut(Arrays.asList(statutEnregistree, statutSoumise, statutEnTraitement))
                .build();
    }

    @Test
    public void devraitRetournerDateHeureStatutSoumise(){
        LocalDateTime expected = LocalDateTime.of(2022, 2, 3, 1, 23, 23);

        assertEquals(this.dreEnregistreeSoumise.getDateHeureSoumission(), expected);
        assertEquals(this.dreEnregistreeSoumiseEnTraitement.getDateHeureSoumission(), expected);
    }

    @Test
    public void devraitRetournerDateSoumissionNull(){
        assertNull(dreEnregistree.getDateHeureSoumission());
    }

    @Test
    public void devraitRetournerStatutCourantEnregistree(){
        TypeStatut expected = TypeStatut.ENREGISTREE;
        assertEquals(dreEnregistree.getStatutCourant(), expected);
    }

    @Test
    public void devraitRetournerStatutCourantSoumise(){
        TypeStatut expected = TypeStatut.SOUMISE;
        assertEquals(dreEnregistreeSoumise.getStatutCourant(), expected);
    }

    @Test
    public void devraitRetournerStatutCourantEnTraitement(){
        TypeStatut expected = TypeStatut.EN_TRAITEMENT;
        assertEquals(dreEnregistreeSoumiseEnTraitement.getStatutCourant(), expected);
    }
}