export const personnelItems = [
    {
        id: 1,
        coursGroupe: {
            session: "HIVER",
            groupe: "030",
            cours: {
                sigle: "INF1120"
            }
        },
        etudiant: {
            nom: "tremblay",
            prenom: "marc",
            codePermanent: "TREM23146985"
        },
        enseignant: {
            nom: "lord",
            prenom: "melanie",
            matricule: "LORM45698732"
        },
        statutCourant: "SOUMISE",
        decisionCourante: null,
        dateHeureSoumission: "2022-01-15T10:40:01"
    },
    {
        id: 2,
        coursGroupe: {
            session: "AUTOMNE",
            groupe: "020",
            cours: {
                sigle: "INF2120"
            }
        },
        etudiant: {
            nom: "marshal",
            prenom: "jean",
            codePermanent: "MARJ63985096"
        },
        enseignant: {
            nom: "lord",
            prenom: "melanie",
            matricule: "LORM45698732"
        },
        statutCourant: "ACCEPTEE",
        decisionCourante: "ACCEPTEE_ENSEIGNANT",
        dateHeureSoumission: "2022-10-20T10:40:01"
    },
    {
        id: 3,
        coursGroupe: {
            session: "HIVER",
            groupe: "040",
            cours: {
                sigle: "INF1070"
            }
        },
        etudiant: {
            nom: "tremblay",
            prenom: "marc",
            codePermanent: "TREM23146985"
        },
        enseignant: {
            nom: "tsheke shele",
            prenom: "johnny",
            matricule: "TSHJM63985213"
        },
        statutCourant: "EN_TRAITEMENT",
        decisionCourante: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2022-01-15T10:40:01",
    }
];
