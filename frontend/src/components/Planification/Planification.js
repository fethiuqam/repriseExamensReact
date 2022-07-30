import React, {useEffect, useState} from 'react';
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import {useForm} from 'react-hook-form';
import Box from "@mui/material/Box";
import {
    Alert,
    Button,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Snackbar,
    TextField
} from "@mui/material";
import {afficherDateHeureIso, comparator} from "../../utils/utils";
import TablePlanification from "../TablePlanification/TablePlanification";

export default function Planification() {

    const [listeCoursGroupe, setListeCoursGroupe] = useState([]);
    const [fetchError, setFetchError] = useState(null);
    const [enChargement, setEnChargement] = useState(true);
    const [colonnes, setColonnes] = useState([
        {name: 'Cours', prop: 'cours.sigle', active: false},
        {name: 'Session', prop: 'session', active: false},
        {name: 'Enseignant', prop: 'enseignant.prenom', active: false},
    ]);

    const {register, handleSubmit, reset, setValue, formState: {errors}} = useForm();

    const [idCoursGroupe, setIdCoursGroupe] = useState(null);
    const [idReprise, setIdReprise] = useState(null);

    const [detailsDialogOpen, setDetailsDialogOpen] = useState(false);
    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarSeverity, setSnackBarSeverity] = useState("success");
    const [snackBarTexte, setSnackBarTexte] = useState("");

    const chargerPlanifications = async () => {

        const API_URL_LISTE_PLANIFICATION = "/api/coursGroupes/planification";

        try {
            const reponse = await fetch(API_URL_LISTE_PLANIFICATION,
                {
                    method: 'get',
                    credentials: 'include'
                });
            if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
            const data = await reponse.json();
            setListeCoursGroupe(data);
            setFetchError(null);
        } catch (err) {
            setFetchError(err.message);
        } finally {
            setEnChargement(false);
        }
    }

    useEffect(() => {
        chargerPlanifications();
    }, []);

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
        setListeCoursGroupe([...listeCoursGroupe].sort(
            comparator(
                colonnes[index].prop,
                colonnes[index].order === 'desc'
            )
        ));
    };

    const onSubmit = (data) => {

        data = {...data, idCoursGroupe};

        const API_URL = idReprise? `/api/reprises/${idReprise}` : "/api/reprises";
        const methode = idReprise? "PUT" : "POST";

        setSnackBarOpen(true);

        const modifierReprise = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: methode,
                        body: JSON.stringify(data),
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
                    setSnackBarSeverity("success");
                    setSnackBarTexte("La planification de la reprise s'est déroulée avec succès.");
                    setSnackBarOpen(true);
                    chargerPlanifications();
                }
            } catch (err) {
                setSnackBarSeverity("error");
                setSnackBarTexte(`"Une erreur est survenue lors de la planification de la reprise. Veuillez réessayer."`);
                setSnackBarOpen(true);
            }
        }
        modifierReprise();
        reset();
        setDetailsDialogOpen(false);
    }

    const modifierPlanification = (item) => {
        setIdCoursGroupe(item.id);

        // cas de modification
        if (item.reprise) {
            setIdReprise(item.reprise.id);
            setValue("dateHeure", item.reprise.dateHeure);
            setValue("dureeMinutes", item.reprise.dureeMinutes);
            setValue("local", item.reprise.local);
            setValue("surveillant", item.reprise.surveillant);
        }else {
            setIdReprise(null);
        }
        setDetailsDialogOpen(true);
    }

    const annulerPlanification = (item) => {
        const API_URL = `/api/reprises/${item.reprise.id}`;

        setSnackBarOpen(true);

        const supprimerReprise = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: "DELETE",
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
                    setSnackBarSeverity("success");
                    setSnackBarTexte("La planification de la reprise a été annulée avec succès.");
                    setSnackBarOpen(true);
                    chargerPlanifications();
                }
            } catch (err) {
                setSnackBarSeverity("error");
                setSnackBarTexte(`"Une erreur est survenue lors de l'annulation de la planification. Veuillez réessayer."`);
                setSnackBarOpen(true);
            }
        }
        supprimerReprise();
    }

    return (
        <>
            <MiseEnPage titre="Planification des reprises d'examen">
                <SectionFormulaire
                    title={'PLANIFICATION DES REPRISES'}>
                    {enChargement
                        ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                            <CircularProgress/>
                        </Box>
                        : fetchError
                            ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                <h3 style={{color: 'red'}}>{fetchError}</h3>
                            </Box>
                            : listeCoursGroupe.length > 0
                                ?
                                <TablePlanification
                                    items={listeCoursGroupe}
                                    colonnes={colonnes}
                                    trier={trier}
                                    modifierPlanification={modifierPlanification}
                                    annulerPlanification={annulerPlanification}
                                />


                                : <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <h3>Aucun cours-groupe à planifier.</h3>
                                </Box>
                    }
                </SectionFormulaire>
            </MiseEnPage>

            <Dialog
                open={detailsDialogOpen}
                onClose={() => {
                    setDetailsDialogOpen(false);
                    reset()
                }}
                fullWidth
            >
                <DialogTitle>Détails de la reprise</DialogTitle>
                <form onSubmit={handleSubmit(onSubmit)}>

                    <DialogContent>
                        {/*<DialogContentText>Veuiller saisir votre message.</DialogContentText>*/}
                        <TextField
                            label="Date-heure "
                            type="datetime-local"
                            margin="normal"
                            fullWidth
                            autoFocus
                            defaultValue={afficherDateHeureIso(new Date())}
                            {...register(
                                "dateHeure",
                                {
                                    required: {
                                        value: true,
                                        message: "Ce Champs est requis."
                                    }
                                })}
                            error={Boolean(errors.dateHeure)}
                            helperText={errors.dateHeure?.message}
                        />
                        <TextField
                            label="Durée en minutes"
                            type="number"
                            margin="normal"
                            fullWidth
                            defaultValue={180}
                            {...register(
                                "dureeMinutes",
                                {
                                    required: {
                                        value: true,
                                        message: "Ce champ est requis."
                                    },
                                    min: {
                                        value: 10,
                                        message: "La durée ne peut pas être inférieure à 10 min."
                                    },
                                    max: {
                                        value: 480,
                                        message: "La durée ne peut pas être supérieure à 480 min."
                                    }
                                })}
                            error={Boolean(errors.dureeMinutes)}
                            helperText={errors.dureeMinutes?.message}
                        />

                        <TextField
                            label="Local"
                            type="text"
                            margin="normal"
                            fullWidth
                            defaultValue=""
                            {...register(
                                "local",
                                {
                                    required: {
                                        value: true,
                                        message: "Ce champ est requis."
                                    },
                                    minLength: {
                                        value: 3,
                                        message: "Le nom du local doit contenir au moins 3 caractères."
                                    }
                                })}
                            error={Boolean(errors.local)}
                            helperText={errors.local?.message}
                        />

                        <TextField
                            label="Surveillant"
                            type="text"
                            margin="normal"
                            fullWidth
                            defaultValue=""
                            {...register(
                                "surveillant",
                                {
                                    required: {
                                        value: true,
                                        message: "Ce champ est requis."
                                    },
                                    minLength: {
                                        value: 3,
                                        message: "Le nom du surveillant doit contenir au moins 3 caractères."
                                    }
                                })}
                            error={Boolean(errors.surveillant)}
                            helperText={errors.surveillant?.message}
                        />
                    </DialogContent>

                    <DialogActions>
                        <Button onClick={() => {
                            setDetailsDialogOpen(false);
                            reset()
                        }}>Annuler</Button>
                        <Button
                            type="submit"
                        >Enregistrer</Button>
                    </DialogActions>
                </form>
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
