import { useCallback, useEffect, useMemo, useState } from "react";
import {
  DataGrid,
  frFR,
  GridActionsCellItem,
  GridRowModes,
  GridToolbarContainer,
  GridToolbarQuickFilter,
} from "@mui/x-data-grid";
import { Alert, Button, Container, Snackbar, Typography } from "@mui/material";
import { Add, Cancel, Delete, Edit, Save } from "@mui/icons-material";
import * as apiClient from "../../api/ApiClient";

/**
 * Liste CRUD pour la gestion d'objets du domaine.
 * Utilise MUI DataGrid: https://mui.com/x/react-data-grid/
 */
export default function ListeAdmin({
  /**
   * Le titre de la liste.
   * Ex.: "Utilisateurs".
   * @type: string
   */
  titre,
  /**
   * Un tableau de définitions de colonnes.
   *
   * @type: GridColDef[]
   * @see https://mui.com/x/api/data-grid/grid-col-def/
   *
   * La propriété `default`, qui représente la valeur par défaut de la colonne,
   * doit obligatoirement être définie pour les GridColDef.
   */
  colonnes,
  /**
   * Le endpoint où chercher les objets gérés.
   * Ex.: "utilisateurs".
   *
   * @type: string
   */
  getEndpoint,
  /**
   * Optionnel.
   * Fonction qui calcule le endpoint pour la mises à jour d'un objet, à partir de la rangée mise à jour.
   * Peut être utile si le endpoint à utiliser dépend des valeurs entrées.
   * Si elle n'est pas fournie, getEndpoint sera utilisé pour les mises à jour.
   *
   * @type: function(rangee: GridRowModel): string
   * (GridRowModel est un Map des noms de colonnes et de leurs valeurs.)
   */
  postEndpoint,
  /**
   * Optionnel.
   * Fonction qui transforme un objet JSON reçu du serveur en rangée.
   *
   * @type: function(objet: Object): GridRowModel
   */
  decoder,
  /**
   * Optionnel.
   * Fonction qui transforme une rangée en object JSON pour la mise à jour.
   *
   * @type: function(rangee: GridRowModel): Object
   */
  encoder,
  /**
   * Optionnel.
   * Fonction qui détermine si une cellule peut être éditée, pour une colonne et une rangée données.
   *
   * @type: function(param: GridCellParam): boolean
   * @see https://mui.com/x/api/data-grid/grid-cell-params/
   */
  estEditable,
}) {
  const [rangees, setRangees] = useState([]);
  const [modesRangees, setModesRangees] = useState({});
  const [chargement, setChargement] = useState(true);
  const [erreur, setErreur] = useState(null);

  const decoderEntite = useMemo(() => decoder ?? ((x) => x), [decoder]);

  colonnes = [
    ...colonnes,
    {
      field: "actions",
      type: "actions",
      getActions: ({ id, row }) =>
        modesRangees[id]?.mode === GridRowModes.Edit
          ? [
              <GridActionsCellItem
                icon={<Save />}
                label="Sauvegarder"
                onClick={() => {
                  setModesRangees({
                    ...modesRangees,
                    [id]: { mode: GridRowModes.View },
                  });
                }}
              />,
              <GridActionsCellItem
                icon={<Cancel />}
                label="Annuler"
                onClick={() => {
                  setModesRangees({
                    ...modesRangees,
                    [id]: {
                      mode: GridRowModes.View,
                      ignoreModifications: true,
                    },
                  });

                  setRangees(rangees.filter((r) => !(r.id === id && r.isNew)));
                }}
              />,
            ]
          : [
              <GridActionsCellItem
                icon={<Edit />}
                label="Modifier"
                onClick={() => {
                  setModesRangees({
                    ...modesRangees,
                    [id]: { mode: GridRowModes.Edit },
                  });
                }}
              />,
              <GridActionsCellItem
                icon={<Delete />}
                label="Supprimer"
                onClick={async () => {
                  try {
                    await apiClient.httpDelete(
                      new URL(row._links.self.href).pathname
                    );
                    setRangees(rangees.filter((r) => r.id !== id));
                  } catch (e) {
                    if (e.cause === 409)
                      setErreur({
                        children: "Impossible de supprimer cet item.",
                        severity: "error",
                      });
                  }
                }}
              />,
            ],
    },
  ];

  useEffect(() => {
    apiClient
      .get("/api/" + getEndpoint)
      .then(
        (payload) =>
          setRangees(
            payload._embedded[getEndpoint].map((rangee, id) => ({
              id,
              ...decoderEntite(rangee),
            }))
          ),
        (erreur) => console.error(erreur.message)
      )
      .finally(() => setChargement(false));
  }, [decoderEntite, getEndpoint]);

  const traiterChangementRangee = useCallback(
    async (rangee) => {
      console.log(rangee);
      const encoderEntite = (rangee) => {
        const entite = Object.fromEntries(
          Object.entries({ ...rangee, id: undefined }).map(([key, val]) => {
            return [
              key,
              Array.isArray(val)
                ? val.map((v) => v?._links?.self?.href ?? v)
                : val,
            ];
          })
        );
        return encoder ? encoder(entite) : entite;
      };

      const endpoint = postEndpoint ? postEndpoint(rangee) : getEndpoint;
      const [method, url] = rangee.isNew
        ? [apiClient.post, "/api/" + endpoint]
        : [apiClient.patch, new URL(rangee._links.self.href).pathname];

      await method(url, encoderEntite(rangee));

      const newRangee = {
        isNew: false,
        ...rangee,
      };

      setRangees(rangees.map((r) => (r.id === newRangee.id ? newRangee : r)));
      return newRangee;
    },
    [encoder, getEndpoint, postEndpoint, rangees]
  );

  return (
    <Container sx={{ p: 3 }}>
      <Typography sx={{ mb: 3 }} variant="h4">
        {titre}
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
        editMode="row"
        rowModesModel={modesRangees}
        onRowEditStart={(_, event) => {
          event.defaultMuiPrevented = true;
        }}
        onRowEditStop={(_, event) => {
          event.defaultMuiPrevented = true;
        }}
        components={{ Toolbar: BarreActions }}
        componentsProps={{
          toolbar: {
            colonnes,
            rangees,
            setRangees,
            setModesRangees,
            showQuickFilter: true,
          },
        }}
        experimentalFeatures={{ newEditingApi: true }}
        processRowUpdate={traiterChangementRangee}
        onProcessRowUpdateError={(e) => {
          setErreur({
            children: e.message,
            severity: "error",
          });
        }}
        initialState={{
          sorting: {
            sortModel: [{ field: colonnes[0].field, sort: "asc" }],
          },
        }}
        loading={chargement}
        isCellEditable={estEditable ?? (() => true)}
      />
      <Snackbar open={!!erreur} onClose={() => setErreur(null)}>
        <Alert {...erreur} onClose={() => setErreur(null)} />
      </Snackbar>
    </Container>
  );
}

function BarreActions({ colonnes, rangees, setRangees, setModesRangees }) {
  const handleClick = () => {
    const id =
      rangees.map(({ id }) => id).reduce((x, y) => (y > x ? y : x)) + 1;
    setRangees((rangees) => [
      {
        id,
        isNew: true,
        ...colonnes.reduce(
          (prev, curr) => ({ ...prev, [curr.field]: curr.default }),
          {}
        ),
      },
      ...rangees,
    ]);
    setModesRangees((modes) => ({
      ...modes,
      [id]: {
        mode: GridRowModes.Edit,
        fieldToFocus: colonnes[0].field,
      },
    }));
  };

  return (
    <GridToolbarContainer sx={{ justifyContent: "space-between" }}>
      <Button startIcon={<Add />} onClick={handleClick}>
        Ajouter
      </Button>
      <GridToolbarQuickFilter />
    </GridToolbarContainer>
  );
}
