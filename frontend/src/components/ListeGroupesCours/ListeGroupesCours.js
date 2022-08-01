import ListeAdmin from "../ListeAdmin/ListeAdmin";
import { TrimestreId } from "../../utils/const";
import { useEffect, useState } from "react";
import * as apiClient from "../../api/ApiClient";

export default function ListeGroupesCours() {
  const [cours, setCours] = useState([]);
  const [enseignants, setEnseignants] = useState([]);

  const formatterCours = (cours) =>
    cours?.sigle && cours?.nom && `${cours.sigle} - ${cours.nom}`;
  const formatterEnseignant = (enseignant) => {
    console.log(enseignant);
    return (
      enseignant?.prenom &&
      enseignant?.nom &&
      enseignant.prenom + " " + enseignant.nom
    );
  };

  useEffect(() => {
    apiClient.get("/api/cours").then((reponse) =>
      setCours(
        reponse._embedded.cours.map((cours) => ({
          ...cours,
          label: formatterCours(cours),
          value: cours.id,
        }))
      )
    );

    apiClient.get("/api/enseignants").then((reponse) =>
      setEnseignants(
        reponse._embedded.utilisateurs.map((enseignant) => ({
          ...enseignant,
          label: formatterEnseignant(enseignant),
          value: enseignant.id,
        }))
      )
    );
  }, []);

  return (
    <ListeAdmin
      titre="Groupes-cours"
      getEndpoint="coursGroupes"
      colonnes={[
        {
          field: "annee",
          headerName: "Année",
          flex: 1,
          editable: true,
          default: "",
        },
        {
          field: "session",
          headerName: "Trimestre",
          flex: 1,
          editable: true,
          default: "",
          type: "singleSelect",
          valueOptions: Object.values(TrimestreId),
        },
        {
          field: "groupe",
          headerName: "Groupe",
          flex: 1,
          editable: true,
          default: "",
        },
        {
          field: "coursId",
          headerName: "Cours",
          flex: 1,
          editable: true,
          type: "singleSelect",
          default: "",
          valueOptions: cours,
          valueFormatter: ({ value }) =>
            value !== "" &&
            formatterCours(cours.find((cours) => cours.id === value)),
          valueParser: (cours) => (typeof cours === Object ? cours.id : cours),
        },
        {
          field: "enseignantId",
          headerName: "Enseignant",
          flex: 1,
          editable: true,
          type: "singleSelect",
          default: "",
          valueOptions: enseignants,
          valueFormatter: ({ value }) =>
            value !== "" &&
            formatterEnseignant(
              enseignants.find((enseignant) => enseignant.id === value)
            ),
          valueParser: (enseignant) =>
            typeof enseignant === Object ? enseignant.id : enseignant,
        },
      ]}
      decoder={(groupe) => ({
        ...groupe,
        coursId: groupe.cours.id,
        enseignantId: groupe.enseignant.id,
      })}
      encoder={({ annee, session, groupe, coursId, enseignantId }) => ({
        annee,
        session,
        groupe,
        cours: "/api/cours/" + coursId,
        enseignant: "/api/enseignants/" + enseignantId,
      })}
    />
  );
}
