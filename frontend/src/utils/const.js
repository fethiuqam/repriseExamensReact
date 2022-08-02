export const STATUTS_PERSONNEL = [
    'SOUMISE',
    'EN_TRAITEMENT',
    'ACCEPTEE',
    'REJETEE',
    'ANNULEE',
    'PLANIFIEE',
    'ABSENCE',
    'COMPLETEE',
    'RETOURNEE',
    'ARCHIVEE'
];

export const STATUTS_ETUDIANT = [
    'ENREGISTREE',
    'SOUMISE',
    'EN_TRAITEMENT',
    'ACCEPTEE',
    'REJETEE',
    'ANNULEE',
    'PLANIFIEE',
    'ABSENCE',
    'COMPLETEE',
    'RETOURNEE'
];

export const STATUTS_ENSEIGNANT = [
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
    COMPLETEE: ["Complétée", "#06D515", "#FFF"],
    RETOURNEE: ["Retournée", "#ED6C02", "#FFF"],
    ARCHIVEE: ["Archivée", "EAECEE", "#666"]
};

export const DECISION_AFFICHAGE = {
    ACCEPTATION_RECOMMANDEE: ["Acceptation recommandée", "success"],
    ACCEPTEE_DIRECTEUR: ["Acceptée par le directeur", "success"],
    ACCEPTEE_ENSEIGNANT: ["Acceptée par l'enseignant", "success"],
    REJET_RECOMMANDE: ["Rejet recommandé", "error"],
    REJETEE_DIRECTEUR: ["Rejetée par le directeur", "error"],
    REJETEE_ENSEIGNANT: ["Rejetée par l'enseignant", "error"],
    RETOURNEE: ["Retournée", "warning"],
    AUCUNE: ["Aucune", "default"]
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
    PlanifierDates: "PlanifierDates",
    GererUsagers: "GererUsagers",
    GererRoles: "GererRoles",
    JugerCommis: "JugerCommis",
    JugerDirecteur: "JugerDirecteur",
    RetournerDemande: "RetournerDemande",
    GererCours: "GererCours",
    ArchiverDemande: "ArchiverDemande"
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

export const StatutId = {
  Enregistree: "ENREGISTREE",
  Soumise: "SOUMISE",
  EnTraitement: "EN_TRAITEMENT",
  Acceptee: "ACCEPTEE",
  Rejetee: "REJETEE",
  Annulee: "ANNULEE",
  Planifiee: "PLANIFIEE",
  Absence: "ABSENCE",
  Completee: "COMPLETEE",
  Retournee: "RETOURNEE",
};

export const DecisionId = {
  AcceptationRecommandee: "ACCEPTATION_RECOMMANDEE",
  AccepteeDirecteur: "ACCEPTEE_DIRECTEUR",
  AccepteeEnseignant: "ACCEPTEE_ENSEIGNANT",
  RejetRecommande: "REJET_RECOMMANDE",
  RejeteeDirecteur: "REJETEE_DIRECTEUR",
  RejeteeEnseignant: "REJETEE_ENSEIGNANT",
  Retournee: "RETOURNEE",
  Aucune: "AUCUNE",
};

export const JugementId = {
  Rejeter: "rejeter",
  Accepter: "accepter",
  Retourner: "retourner",
  AnnulerRejet: "annuler-rejet",
};

export const TrimestreId = {
  ETE: "ETE",
  AUTOMNE: "AUTOMNE",
  HIVER: "HIVER",
};

export const MessageId = {
    DemandeCommis: 'DEMANDE_COMMIS',
    ReponseEtudiant: 'REPONSE_ETUDIANT'
}