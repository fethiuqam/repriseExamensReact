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
        decisionCourante: {
            typeDecision: "AUCUNE"
        },
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
        decisionCourante: {
            typeDecision: "ACCEPTEE_ENSEIGNANT"
        },
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
        decisionCourante: {
            typeDecision: "ACCEPTATION_RECOMMANDEE",
            details: "Le motif et les dates sont corrects"
        },
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

export const mockUtilisateurs = {
  _embedded: {
    utilisateurs: [
      {
        _links: {
          coursGroupeListe: {
            href: "http://localhost:8080/api/enseignants/1/coursGroupeListe",
          },
          enseignant: {
            href: "http://localhost:8080/api/enseignants/1",
          },
          roles: {
            href: "http://localhost:8080/api/enseignants/1/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/enseignants/1",
          },
        },
        codeMs: "enseignant1",
        email: "lord.melanie.courrier.uqam.ca",
        matricule: "LORM45698732",
        nom: "lord",
        permissions: [],
        prenom: "melanie",
        type: "enseignant",
      },
      {
        _links: {
          coursGroupeListe: {
            href: "http://localhost:8080/api/enseignants/2/coursGroupeListe",
          },
          enseignant: {
            href: "http://localhost:8080/api/enseignants/2",
          },
          roles: {
            href: "http://localhost:8080/api/enseignants/2/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/enseignants/2",
          },
        },
        codeMs: "enseignant2",
        email: "berger.jack.courrier.uqam.ca",
        matricule: "BERJ32695723",
        nom: "jack",
        permissions: [],
        prenom: "berger",
        type: "enseignant",
      },
      {
        _links: {
          coursGroupes: {
            href: "http://localhost:8080/api/etudiants/3/coursGroupes",
          },
          demandes: {
            href: "http://localhost:8080/api/etudiants/3/demandes",
          },
          etudiant: {
            href: "http://localhost:8080/api/etudiants/3",
          },
          roles: {
            href: "http://localhost:8080/api/etudiants/3/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/etudiants/3",
          },
        },
        codeMs: "etudiant1",
        codePermanent: "TREM23146985",
        email: "tremblay.marc.courrier.uqam.ca",
        nom: "tremblay",
        permissions: [],
        prenom: "marc",
        telephone: "5146667777",
        type: "etudiant",
      },
      {
        _links: {
          coursGroupes: {
            href: "http://localhost:8080/api/etudiants/4/coursGroupes",
          },
          demandes: {
            href: "http://localhost:8080/api/etudiants/4/demandes",
          },
          etudiant: {
            href: "http://localhost:8080/api/etudiants/4",
          },
          roles: {
            href: "http://localhost:8080/api/etudiants/4/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/etudiants/4",
          },
        },
        codeMs: "etudiant2",
        codePermanent: "BEAJ69326598",
        email: "beaudry.jean.courrier.uqam.ca",
        nom: "beaudry",
        permissions: [],
        prenom: "jean",
        telephone: "5145553333",
        type: "etudiant",
      },
      {
        _links: {
          coursGroupes: {
            href: "http://localhost:8080/api/etudiants/5/coursGroupes",
          },
          demandes: {
            href: "http://localhost:8080/api/etudiants/5/demandes",
          },
          etudiant: {
            href: "http://localhost:8080/api/etudiants/5",
          },
          roles: {
            href: "http://localhost:8080/api/etudiants/5/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/etudiants/5",
          },
        },
        codeMs: "etudiant3",
        codePermanent: "TREM23146985",
        email: "tremblay.marc.courrier.uqam.ca",
        nom: "tremblay",
        permissions: [],
        prenom: "marc",
        telephone: "5146667777",
        type: "etudiant",
      },
      {
        _links: {
          coursGroupes: {
            href: "http://localhost:8080/api/etudiants/6/coursGroupes",
          },
          demandes: {
            href: "http://localhost:8080/api/etudiants/6/demandes",
          },
          etudiant: {
            href: "http://localhost:8080/api/etudiants/6",
          },
          roles: {
            href: "http://localhost:8080/api/etudiants/6/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/etudiants/6",
          },
        },
        codeMs: "etudiant4",
        codePermanent: "BEAJ69326598",
        email: "beaudry.jean.courrier.uqam.ca",
        nom: "beaudry",
        permissions: [],
        prenom: "jean",
        telephone: "5145553333",
        type: "etudiant",
      },
      {
        _embedded: {
          roles: [
            {
              _links: {
                self: {
                  href: "http://localhost:8080/api/roles/1{?projection}",
                  templated: true,
                },
                utilisateurs: {
                  href: "http://localhost:8080/api/roles/1/utilisateurs",
                },
              },
              id: 1,
              nom: "commis",
            },
          ],
        },
        _links: {
          personnel: {
            href: "http://localhost:8080/api/personnels/7",
          },
          roles: {
            href: "http://localhost:8080/api/personnels/7/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/personnels/7",
          },
        },
        codeMs: "commis",
        email: null,
        matricule: "1",
        nom: "lauzon",
        permissions: [
          "AfficherDRE",
          "PlanifierDates",
          "ListerDRE",
          "AfficherJustificatifs",
          "JugerEnseignant",
          "JugerCommis",
        ],
        prenom: "manon",
        type: "personnel",
      },
      {
        _embedded: {
          roles: [
            {
              _links: {
                self: {
                  href: "http://localhost:8080/api/roles/2{?projection}",
                  templated: true,
                },
                utilisateurs: {
                  href: "http://localhost:8080/api/roles/2/utilisateurs",
                },
              },
              id: 2,
              nom: "directeur",
            },
            {
              _links: {
                self: {
                  href: "http://localhost:8080/api/roles/3{?projection}",
                  templated: true,
                },
                utilisateurs: {
                  href: "http://localhost:8080/api/roles/3/utilisateurs",
                },
              },
              id: 3,
              nom: "admin",
            },
          ],
        },
        _links: {
          personnel: {
            href: "http://localhost:8080/api/personnels/8",
          },
          roles: {
            href: "http://localhost:8080/api/personnels/8/roles{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/personnels/8",
          },
        },
        codeMs: "directeur",
        email: null,
        matricule: "2",
        nom: "beaudry",
        permissions: [
          "AfficherDRE",
          "ListerDRE",
          "GererRoles",
          "AfficherJustificatifs",
          "GererUsagers",
          "JugerDirecteur",
        ],
        prenom: "eric",
        type: "personnel",
      },
    ],
  },
  _links: {
    profile: {
      href: "http://localhost:8080/api/profile/utilisateurs",
    },
    search: {
      href: "http://localhost:8080/api/utilisateurs/search",
    },
    self: {
      href: "http://localhost:8080/api/utilisateurs",
    },
  },
  page: {
    number: 0,
    size: 20,
    totalElements: 8,
    totalPages: 1,
  },
};

export const mockRoles = {
  _embedded: {
    roles: [
      {
        _links: {
          role: {
            href: "http://localhost:8080/api/roles/1{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/roles/1",
          },
          utilisateurs: {
            href: "http://localhost:8080/api/roles/1/utilisateurs",
          },
        },
        id: 1,
        nom: "commis",
      },
      {
        _links: {
          role: {
            href: "http://localhost:8080/api/roles/2{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/roles/2",
          },
          utilisateurs: {
            href: "http://localhost:8080/api/roles/2/utilisateurs",
          },
        },
        id: 2,
        nom: "directeur",
      },
      {
        _links: {
          role: {
            href: "http://localhost:8080/api/roles/3{?projection}",
            templated: true,
          },
          self: {
            href: "http://localhost:8080/api/roles/3",
          },
          utilisateurs: {
            href: "http://localhost:8080/api/roles/3/utilisateurs",
          },
        },
        id: 3,
        nom: "admin",
      },
    ],
  },
  _links: {
    profile: {
      href: "http://localhost:8080/api/profile/roles",
    },
    search: {
      href: "http://localhost:8080/api/roles/search",
    },
    self: {
      href: "http://localhost:8080/api/roles",
    },
  },
  page: {
    number: 0,
    size: 20,
    totalElements: 3,
    totalPages: 1,
  },
};
