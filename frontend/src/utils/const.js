
export const STATUTS = [
    'ENREGISTREE',
    'SOUMISE',
    'EN_TRAITEMENT',
    'ACCEPTEE',
    'REJETEE',
    'ANNULEE',
    'PLANIFIEE',
    'ABSENCE',
    'COMPLETEE'
];

export const STATUT_AFFICHAGE = {
    ENREGISTREE: ["Enregistrée", "#AED6F1", "#555"],
    SOUMISE: ["Soumise", "#55B2FA", "#FFF"],
    EN_TRAITEMENT: ["En traitement", "#B07FFC", "#FFF"],
    ACCEPTEE: ["Acceptée", "#A5F8C4", "#666"],
    REJETEE: ["Rejetée", "#F91A21", "#FFF"],
    ANNULEE: ["Annulée", "#FBE90E", "#666"],
    PLANIFIEE: ["Planifiée", "#0EEDFB", "#666"],
    ABSENCE: ["Absence", "#FC6538", "#FFF"],
    COMPLETEE: ["Complétée", "#06D515", "#FFF"]
};

export const DECISION_AFFICHAGE = {
    ACCEPTATION_RECOMMANDEE: ["Acceptation recommandée", "success"],
    ACCEPTEE_DIRECTEUR: ["Acceptée par le directeur", "success"],
    ACCEPTEE_ENSEIGNANT: ["Acceptée par l'enseignant", "success"],
    REJET_RECOMMANDE: ["Rejet recommandé", "error"],
    REJETEE_DIRECTEUR: ["Rejetée par le directeur", "error"],
    REJETEE_ENSEIGNANT: ["Rejetée par l'enseignant", "error"],
    AUCUNE: ["Aucune", "default"]
};

export const SESSION_AFFICHAGE = {
    HIVER: "Hiver",
    AUTOMNE: "Automne",
    ETE: "Été"
};

export const MOTIF_AFFICHAGE = {
    MEDICAL: "Hospitalisation",
    DECES: "Décès",
    ACCIDENT: "Accident",
    JUDICIAIRE: "Convocation à un tribunal",
    RELIGIEUX: "Motif religieux",
    AUTRE: "Autre"
};

export const STYLE_MESSAGE_RECU = {
    borderRadius: '10px',
    padding: '10px',
    display: 'inline-block',
    color: '#FFFFFF',
    maxWidth: '80%',
    backgroundColor: '#FF6968'
}

export const STYLE_MESSAGE_ENVOYE = {
    borderRadius: '10px',
    padding: '10px',
    display: 'inline-block',
    color: '#FFFFFF',
    maxWidth: '80%',
    backgroundColor: '#0695FF'
}

export const Permission = {
  ListerDRE: "ListerDRE",
  AfficherDRE: "AfficherDRE",
  AfficherJustificatifs: "AfficherJustificatifs",
  JugerRecevabilite: "JugerRecevabilite",
  PlanifierDates: "PlanifierDates",
  GererUsagers: "GererUsagers",
  GererRoles: "GererRoles",
};

export const TypeId = {
  Etudiant: "etudiant",
  Enseignant: "enseignant",
  Personnel: "personnel",
};

export const types = {
  [TypeId.Etudiant]: {
    id: TypeId.Etudiant,
    nom: "Étudiant",
    endpoint: "etudiants",
    nomIdentifiant: "codePermanent",
  },
  [TypeId.Enseignant]: {
    id: TypeId.Enseignant,
    nom: "Enseignant",
    endpoint: "enseignants",
    nomIdentifiant: "matricule",
  },
  [TypeId.Personnel]: {
    id: TypeId.Personnel,
    nom: "Personnel",
    endpoint: "personnels",
    nomIdentifiant: "matricule",
  },
};
