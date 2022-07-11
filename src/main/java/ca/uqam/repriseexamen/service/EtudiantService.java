package ca.uqam.repriseexamen.service;

import java.util.List;
import java.util.Optional;

import ca.uqam.repriseexamen.dto.LigneHistoriqueEtudiantDTO;
import ca.uqam.repriseexamen.model.Etudiant;

public interface EtudiantService {
    Optional<Etudiant> getEtudiant(Long idEtudiant);

    List<LigneHistoriqueEtudiantDTO> getHistoriqueEtudiant(long id);
}
