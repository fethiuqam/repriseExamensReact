package ca.uqam.repriseexamen.service;

import java.util.Optional;

import ca.uqam.repriseexamen.model.Etudiant;

public interface EtudiantService {
    Optional<Etudiant> getEtudiant(Long idEtudiant);
}
