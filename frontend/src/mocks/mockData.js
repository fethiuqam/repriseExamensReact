export const personnelItems = [
    {
        id: 1,
        coursGroupe: {
            session: "HIVER",
            groupe: "030",
            annee: "2022",
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
            annee: "2022",
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
            annee: "2022",
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


export const APIgetDemandesResponse = [
    {
        id: 1,
        session: "HIVER",
        groupe: "030",
        statut: "SOUMISE",
        decision: null,
        dateHeureSoumission: "2000-01-15T10:40:01",
        sigleCours: "INF1120",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "melanie lord",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 2,
        session: "AUTOMNE",
        groupe: "020",
        statut: "ACCEPTEE",
        decision: "ACCEPTEE_ENSEIGNANT",
        dateHeureSoumission: "2021-10-20T10:40:01",
        sigleCours: "INF2120",
        nomEtudiant: "jean marshal",
        nomEnseignant: "melanie lord",
        codePermanentEtudiant: "MARJ63985096",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 3,
        session: "HIVER",
        groupe: "040",
        statut: "EN_TRAITEMENT",
        decision: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2016-01-15T11:40:01",
        sigleCours: "INF1070",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "johnny tsheke shele",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 4,
        session: "HIVER",
        groupe: "040",
        statut: "EN_TRAITEMENT",
        decision: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2016-01-15T11:40:01",
        sigleCours: "INF1070",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "johnny tsheke shele",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 5,
        session: "HIVER",
        groupe: "040",
        statut: "EN_TRAITEMENT",
        decision: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2018-01-15T11:40:01",
        sigleCours: "INF1070",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "johnny tsheke shele",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 6,
        session: "HIVER",
        groupe: "040",
        statut: "EN_TRAITEMENT",
        decision: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2019-11-15T11:40:01",
        sigleCours: "INF1070",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "johnny tsheke shele",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
    {
        id: 7,
        session: "HIVER",
        groupe: "040",
        statut: "EN_TRAITEMENT",
        decision: "ACCEPTEE_COMMIS",
        dateHeureSoumission: "2019-06-15T11:40:01",
        sigleCours: "INF1070",
        nomEtudiant: "marc tremblay",
        nomEnseignant: "johnny tsheke shele",
        codePermanentEtudiant: "TREM23146985",
        matriculeEnseignant: "LORM45698732"
    },
];
