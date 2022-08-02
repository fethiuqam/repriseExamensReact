import React, {useContext, useEffect, useState} from 'react';
import FiltreListeDRE from "../FiltreListeDRE/FiltreListeDRE";
import TableListeDRE from "../TableListeDRE/TableListeDRE";
import AuthContext from "../../context/AuthProvider";
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import Box from "@mui/material/Box";
import {
    Alert, Button,
    CircularProgress, Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Snackbar
} from "@mui/material";
import {comparator, estArchivable, statutsUtilisteurs} from "../../utils/utils";
import {TypeId} from "../../utils/const";


export default function ListeDRE() {

    const {type, id} = useContext(AuthContext);

    // filtre
    const filtreInitial = {
        statuts: statutsUtilisteurs(type),
        etudiant: '',
        enseignant: '',
        cours: ''
    }
    const [listeDRE, setListeDRE] = useState([]);
    const [listeDREFiltree, setListeDREFiltree] = useState([]);
    const [filtre, setFiltre] = useState(filtreInitial);

    const filtrer = () => {
        let filtreItems = listeDRE.filter(item =>
            filtre.statuts.includes(item.statutCourant)
            && item.coursGroupe.cours.sigle.toUpperCase().includes(filtre.cours.toUpperCase()));

        if (type !== TypeId.Etudiant) {
            filtreItems = filtreItems.filter(item =>
                item.etudiant.codePermanent.toUpperCase().includes(filtre.etudiant.toUpperCase())
                || item.etudiant.nom.toUpperCase().includes(filtre.etudiant.toUpperCase())
                || item.etudiant.prenom.toUpperCase().includes(filtre.etudiant.toUpperCase()));
        }

        if (type !== TypeId.Enseignant) {
            filtreItems = filtreItems.filter(item =>
                item.enseignant.matricule.toUpperCase().includes(filtre.enseignant.toUpperCase())
                || item.enseignant.nom.toUpperCase().includes(filtre.enseignant.toUpperCase())
                || item.enseignant.prenom.toUpperCase().includes(filtre.enseignant.toUpperCase()));
        }
        setListeDREFiltree(filtreItems);
        setAArchiver(AArchiver.filter(x => filtreItems.map(y => y.id).includes(x)))
    };

    useEffect(() => {
        filtrer();
        // eslint-disable-next-line
    }, [filtre, listeDRE]);

    // chargement
    const [fetchError, setFetchError] = useState(null);
    const [enChargement, setEnChargement] = useState(true);

    const fetchItems = async () => {
        const API_URL = "/api/demandes";
        try {
            const reponse = await fetch(API_URL,
                {
                    method: 'GET',
                    credentials: 'include'
                });
            if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
            const listedre = await reponse.json();
            setListeDRE(listedre);
            setFetchError(null);
        } catch (err) {
            setFetchError(err.message);
        } finally {
            setEnChargement(false);
        }
    }

    useEffect(() => {
        fetchItems();
    }, [type, id]);

    // tableDemande
    const [colonnes, setColonnes] = useState([
        ...[{name: 'Soumise le', prop: 'dateHeureSoumission', active: false}],
        ...(type === TypeId.Etudiant ? [] : [{name: 'Étudiant', prop: 'etudiant.prenom', active: false}]),
        ...(type === TypeId.Enseignant ? [] : [{name: 'Enseignant', prop: 'enseignant.prenom', active: false}]),
        ...[{name: 'Cours', prop: 'coursGroupe.cours.sigle', active: false},
            {name: 'Session', prop: 'coursGroupe.session', active: false},
            {name: 'Statut', prop: 'statutCourant', active: false}],
        ...(type === TypeId.Personnel ? [{name: 'Décision', prop: 'decisionCourante', active: false}] : [])
    ]);

    const trier = (index) => {
        setColonnes(
            colonnes.map((colonne, i) => ({
                ...colonne,
                active: index === i,
                order:
                    (index === i &&
                        (colonne.order === 'desc' ? 'asc' : 'desc')) ||
                    undefined
            }))
        );
        setListeDREFiltree([...listeDREFiltree].sort(
            comparator(
                colonnes[index].prop,
                colonnes[index].order === 'desc'
            )
        ));
    };

    // archivage
    const [AArchiver, setAArchiver] = useState([]);

    const selectionnerItemAArchiver = (e, id) => {
        if (e.target.checked) {
            setAArchiver([...AArchiver, id]);
        } else {
            const newSelected = AArchiver.filter(x => x !== id)
            setAArchiver(newSelected);
        }
    }

    const archiverTout = (e) => {
        if (e.target.checked) {
            setAArchiver(listeDREFiltree.filter(x => estArchivable(x)).map(x => x.id));
        } else {
            setAArchiver([]);
        }
    }

    // Message succes / erreur / confirmation
    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarSeverity, setSnackBarSeverity] = useState("success");
    const [snackBarTexte, setSnackBarTexte] = useState("");
    const [confirmationDialogOpen, setConfirmationDialogOpen] = useState(false);

    const archiverSelection = () => {
        let counter = 0;
        AArchiver.forEach(id => {
            const API_URL_ARCHIVER = `/api/demandes/${id}/archiver`;

            const archiverDemande = async () => {
                try {
                    const reponse = await fetch(API_URL_ARCHIVER,
                        {
                            method: 'PATCH',
                            headers: {
                                'Content-type': 'application/json; charset=UTF-8'
                            },
                            credentials: 'include'
                        });
                    if (reponse.status === 401) {
                        setSnackBarSeverity("error");
                        setSnackBarTexte("Utilisateur non autorisé.");
                        setSnackBarOpen(true);
                    } else if (!reponse.ok) throw Error();
                    else {
                        ++counter;
                        if (counter === AArchiver.length) {
                            setAArchiver([]);
                            setSnackBarSeverity("success");
                            setSnackBarTexte(`L'archivage de ${AArchiver.length} demande(s) s'est déroulé avec succès.`);
                            setSnackBarOpen(true);
                            fetchItems();
                        }
                    }
                } catch (err) {
                    setSnackBarSeverity("error");
                    setSnackBarTexte(`"Une erreur est survenue lors de l'archivage des demandes. Veuillez réessayer."`);
                    setSnackBarOpen(true);
                }
            }
            archiverDemande();
        })
        setConfirmationDialogOpen(false);
    }

    return (
        <>
            <MiseEnPage titre="Liste des demandes de reprise d'examen">
                <SectionFormulaire
                    title={'FILTRE'}>
                    <FiltreListeDRE
                        filtre={filtre}
                        setFiltre={setFiltre}
                    />
                </SectionFormulaire>
                <SectionFormulaire
                    title={'LISTE DES DEMANDES'}>
                    {enChargement
                        ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                            <CircularProgress/>
                        </Box>
                        : fetchError
                            ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                <h3 style={{color: 'red'}}>{fetchError}</h3>
                            </Box>
                            : listeDREFiltree.length > 0
                                ?
                                <TableListeDRE
                                    items={listeDREFiltree}
                                    colonnes={colonnes}
                                    trier={trier}
                                    type={type}
                                    AArchiver={AArchiver}
                                    selectionnerItemAArchiver={selectionnerItemAArchiver}
                                    archiverTout={archiverTout}
                                    setConfirmationDialogOpen={setConfirmationDialogOpen}
                                />

                                : <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <h3>Aucune demande à afficher.</h3>
                                </Box>
                    }
                </SectionFormulaire>
            </MiseEnPage>

            <Dialog
                open={confirmationDialogOpen}
                onClose={() => setConfirmationDialogOpen(false)}
                fullWidth
            >
                <DialogTitle>Confirmer l'archivage des demandes</DialogTitle>
                <DialogContent>
                    <DialogContentText>Êtes vous sûr de vouloir archiver les demandes séléctionnées?</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={() => setConfirmationDialogOpen(false)}>
                        Annuler
                    </Button>
                    <Button onClick={archiverSelection}>Oui, je confirme</Button>
                </DialogActions>
            </Dialog>

            <Snackbar
                style={{width: "50%"}}
                anchorOrigin={{vertical: "top", horizontal: "center"}}
                open={snackBarOpen}
                autoHideDuration={5000}
                onClose={() => setSnackBarOpen(false)}
            >
                <Alert
                    onClose={() => setSnackBarOpen(false)}
                    severity={snackBarSeverity} sx={{width: '100%'}}
                >
                    {snackBarTexte}
                </Alert>
            </Snackbar>
        </>
    );
}
