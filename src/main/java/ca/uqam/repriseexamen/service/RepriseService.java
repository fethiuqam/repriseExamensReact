package ca.uqam.repriseexamen.service;

import ca.uqam.repriseexamen.dto.RepriseDTO;
import ca.uqam.repriseexamen.model.Reprise;

public interface RepriseService {
    Reprise planifierReprise(RepriseDTO repriseDTO);

    void annulerPlanification(Long id);

    Reprise modifierPlanification(RepriseDTO repriseDTO, Long id);
}
