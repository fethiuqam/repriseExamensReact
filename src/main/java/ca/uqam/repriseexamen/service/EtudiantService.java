package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.model.Etudiant;

import java.util.Optional;

public interface EtudiantService {
    Optional<Etudiant> getEtudiant(Long idEtudiant);
}
