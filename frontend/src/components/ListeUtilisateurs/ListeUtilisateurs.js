import { Box } from "@mui/material";
import { useEffect, useState } from "react";
import { TypeId, types } from "../../utils/const";
import ListeAdmin from "../ListeAdmin/ListeAdmin";
import * as apiClient from "../../api/ApiClient";
import { Lock } from "@mui/icons-material";
import ChipBox from "../ChipBox/ChipBox";
import GridMultiSelect from "../GridMultiSelect/GridMultiSelect";

export default function ListeUtilisateurs() {
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    apiClient
      .get("/api/roles")
      .then((reponse) => setRoles(reponse._embedded.roles));
  }, [setRoles]);

  const renderRoles = (roles) => (
    <ChipBox labels={roles.map(({ nom }) => nom)} />
  );

  const colonnes = [
    {
      field: "code",
      headerName: "Identifiant",
      flex: 1,
      minWidth: 125,
      editable: true,
      default: "",
    },
    {
      field: "codeMs",
      headerName: "Code MS",
      flex: 1,
      minWidth: 100,
      editable: true,
      default: "",
    },
    {
      field: "motDePasse",
      headerName: "Mot de passe",
      flex: 1,
      minWidth: 105,
      editable: true,
      default: "",
      renderCell: () => (
        <Box
          sx={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
          }}
        >
          <Lock />
        </Box>
      ),
    },
    {
      field: "prenom",
      headerName: "Prénom",
      flex: 1,
      editable: true,
      default: "",
    },
    {
      field: "nom",
      headerName: "Nom",
      flex: 1,
      minWidth: 80,
      editable: true,
      default: "",
    },
    {
      field: "email",
      headerName: "Courriel",
      flex: 1,
      minWidth: 225,
      editable: true,
      default: "",
    },
    {
      field: "type",
      type: "singleSelect",
      headerName: "Type",
      flex: 1,
      minWidth: 95,
      editable: true,
      default: TypeId.Personnel,
      valueOptions: Object.values(TypeId),
      valueFormatter: ({ value }) => value && types[value].nom,
    },
    {
      field: "roles",
      headerName: "Roles",
      flex: 1,
      minWidth: 240,
      editable: true,
      default: [],
      renderEditCell: (props) => (
        <GridMultiSelect
          {...props}
          items={roles}
          label="nom"
          renderValue={renderRoles}
        />
      ),
      renderCell: ({ value }) => value && renderRoles(value),
    },
  ];

  const decoderUtilisateur = (utilisateur) => ({
    ...utilisateur,
    code: utilisateur[types[utilisateur.type].nomIdentifiant],
    roles: utilisateur._embedded?.roles,
  });

  function encoderUtilisateur(rangee) {
    return {
      ...rangee,
      [types[rangee.type].nomIdentifiant]: rangee.code,
    };
  }

  return (
    <ListeAdmin
      titre="Utilisateurs"
      colonnes={colonnes}
      getEndpoint="utilisateurs"
      postEndpoint={(rangee) => types[rangee.type].endpoint}
      decoder={decoderUtilisateur}
      encoder={encoderUtilisateur}
      estEditable={({ row, field }) => row.isNew || field !== "type"}
    />
  );
}
