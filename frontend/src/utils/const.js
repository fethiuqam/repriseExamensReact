
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
    ENREGISTREE: "Enregistrée",
    SOUMISE: "Soumise",
    EN_TRAITEMENT: "En traitement",
    ACCEPTEE: "Acceptée",
    REJETEE: "Rejetée",
    ANNULEE: "Annulée",
    PLANIFIEE: "Planifiée",
    ABSENCE: "Absence",
    COMPLETEE: "Complétée"
}

export const DECISION_AFFICHAGE = {
    ACCEPTEE_COMMIS: "Acceptée par le commis",
    ACCEPTEE_DIRECTEUR: "Acceptée par le directeur",
    ACCEPTEE_ENSEIGNANT: "Acceptée par l'enseignant",
    REJETEE_COMMIS: "Rejetée par le commis",
    REJETEE_DIRECTEUR: "Rejetée par le directeur",
    REJETEE_ENSEIGNANT: "Rejetée par l'enseignant",

}

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