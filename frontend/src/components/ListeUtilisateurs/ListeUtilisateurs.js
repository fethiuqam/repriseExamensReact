import { useEffect, useState } from "react";
import { TypeId, types } from "../../shared/constants";
import { DataGrid, frFR } from "@mui/x-data-grid";
import { Chip, Container, Paper, Typography } from "@mui/material";

export default function ListeUtilisateurs() {

  const [rangees, setRangees] = useState([]);

  const renderRoles = (params) => (
    <Paper
      variant="outlined"
      sx={{
        width: "100%",
        display: "flex",
        justifyContent: "begin",
        flexWrap: "wrap",
        listStyle: "none",
        p: 0.5,
        m: 0,
      }}
      component="ul"
    >
      {params.value ? (
        params.value?.map((role, index) => (
          <li key={index}>
            <Chip color="primary" sx={{ m: 0.5 }} label={role.nom} />
          </li>
        ))
      ) : (
        <Chip sx={{ visibility: "hidden" }} />
      )}
    </Paper>
  );

  const colonnes = [
    { field: "code", headerName: "Identifiant", flex: 0.9 },
    { field: "prenom", headerName: "Prénom", flex: 0.7 },
    { field: "nom", headerName: "Nom", flex: 0.65 },
    { field: "email", headerName: "Courriel", flex: 1.2 },
    { field: "type", headerName: "Type", flex: 0.8 },
    { field: "roles", headerName: "Roles", flex: 1, renderCell: renderRoles },
  ];

  useEffect(() => {
    function fetchSet(endpoint, setter) {
      return fetch("/api/" + endpoint, {
          credentials: 'include',
      })
        .then((reponse) => reponse.json())
        .then(
          (payload) => setter(payload._embedded[endpoint]),
          (erreur) => console.error(erreur.message)
        );
    }

    const parseUtilisateurs = (utilisateurs) =>
      utilisateurs.map(
        (
          { codePermanent, matricule, prenom, nom, email, type, _embedded },
          id
        ) => ({
          id,
          code: type === TypeId.Etudiant ? codePermanent : matricule,
          prenom,
          nom,
          email,
          type: types[type].nom,
          roles: _embedded?.roles,
        })
      );

    Promise.all([
      fetchSet("utilisateurs", (utilisateurs) =>
        setRangees(parseUtilisateurs(utilisateurs))
      ),
    ]);
  }, []);

  return (
    <Container sx={{ p: 3 }}>
      <Typography sx={{ mb: 3 }} variant="h4">
        Utilisateurs
      </Typography>
      <DataGrid
        autoHeight
        rows={rangees}
        columns={colonnes}
        localeText={frFR.components.MuiDataGrid.defaultProps.localeText}
        disableSelectionOnClick
        sx={{
          "&.MuiDataGrid-root--densityStandard .MuiDataGrid-cell": {
            py: "10px",
          },
        }}
        getRowHeight={() => "auto"}
      />
    </Container>
  );
}
