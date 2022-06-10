package ca.uqam.repriseexamen.dto;

import ca.uqam.repriseexamen.model.Session;
import ca.uqam.repriseexamen.model.TypeStatut;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class LigneDRECommisDTO {

    private Long id;
    private LocalDateTime dateHeureSoumission;
    private TypeStatut statutCourant;
    private String nomEtudiant;
    private String codePermanentEtudiant;
    private String nomEnseignat;
    private String matriculeEnseignat;
    private String sigleCours;
    private String groupe;
    private Session session;

}
