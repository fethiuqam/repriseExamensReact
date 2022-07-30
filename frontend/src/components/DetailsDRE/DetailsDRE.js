import '../../styles/StyleEtudiant.css'
import React, {useContext, useEffect, useState} from 'react';
import AuthContext from "../../context/AuthProvider";
import {Link, useParams} from "react-router-dom";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import SectionFormulaire from '../SectionFormulaire/SectionFormulaire';
import Box from "@mui/material/Box";
import {
    Button,
    CircularProgress,
    Container,
    Stack,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Snackbar,
    Alert } from "@mui/material";
import JugerDRE from "../JugerDRE/JugerDRE";
import TableInfosEnseignant from '../TableInfoEnseignant/TableInforEnseignant';
import TableDetailsAbsence from '../TableDetailsAbsence/TableDetailsAbsence';
import TableInfosEtudiant from '../TableInfosEtudiant/TableInfosEtudiant';
import TableInfosDemande from '../TableInfosDemande/TableInfosDemande';
import KeyboardReturnIcon from "@mui/icons-material/KeyboardReturn";
import MessagesDRE from "../MessagesDRE/MessagesDRE";
import HistoriqueEtudiant from "../HistoriqueEtudiant/HistoriqueEtudiant";
import PlanificationDetails from "../PlanificationDetails/PlanificationDetails";
import * as apiClient from "../../api/ApiClient";

export default function DetailsDRE() {

    const {type, permissions} = useContext(AuthContext);
    const [enChargement, setEnChargement] = useState(true);
    const [dre, setDre] = useState(null);
    const {idDRE} = useParams();
    let juge = null;

    const [confirmationDialogOpen, setConfirmationDialogOpen] = useState(false);
    const [confirmationDialogTitre, setConfirmationDialogTitre] = useState("");
    const [confirmationDialogContenu, setConfirmationDialogContenu] = useState("");
    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarSeverity, setSnackBarSeverity] = useState("success");
    const [snackBarTexte, setSnackBarTexte] = useState("");
    const [snackBarTexteSucces, setSnackBarTexteSucces] = useState("");
    const [snackBarTexteError, setSnackBarTexteError] = useState("");

    const handleAnnulerDRE = () => {
        setConfirmationDialogOpen(true);
        setConfirmationDialogTitre("Confirmer l'annulation de la demande");
        setConfirmationDialogContenu("Êtes vous sûr de vouloir annuler la demande?");
        setSnackBarTexteSucces("La demande a été annulée avec succès.");
        setSnackBarTexteError("Un problème est survenu lors de l'annulation de la demande. Veuillez réessayer.")
    }

    const annulerDemande = () => {
        setSnackBarOpen(true);
        const API_URL = `/api/demandes/${idDRE}/annuler-etudiant`;
        const patch = {idDRE};
        const annulerDRE = async () => {
            try {
                await apiClient.patch(API_URL, patch);
                setSnackBarSeverity("success");
                setSnackBarTexte(snackBarTexteSucces);
                setSnackBarOpen(true);
                checherDRE();
            } catch (err) {
                setSnackBarSeverity("error");
                setSnackBarTexte(
                    err.cause === 406
                        ? "Cette action n'est pas permise."
                        : snackBarTexteError
                );
                setSnackBarOpen(true);
            }
        }
        annulerDRE();
        setConfirmationDialogOpen(false);
    }

    switch (type) {
        case "enseignant" :
            juge = "enseignant";
            break;
        case "personnel" :
            if (permissions.includes("JugerCommis"))
                juge = "commis";
            else if (permissions.includes("JugerDirecteur"))
                juge = "directeur";
            break;
        default:
    }

    const API_URL = `/api/demandes/${idDRE}`;

    const checherDRE = async () => {
        try {
            const reponse = await fetch(API_URL,
                {
                    method: 'get',
                    credentials: 'include'
                });
            if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
            const dre = await reponse.json();
            setDre(dre);
        } catch (err) {
            console.log(err);
        } finally {
            setEnChargement(false);
        }
    }

    useEffect(() => {
        window.scrollTo(0, 0);
        checherDRE();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [idDRE]);

    return (
        <MiseEnPage titre="Détails de la demande">
            {enChargement
                ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                    <CircularProgress/>
                </Box>
                : <>
                    {dre.etudiant
                        ? <SectionFormulaire title={'INFORMATIONS PERSONNELLES'}>
                            <TableInfosEtudiant etudiant={dre.etudiant}/>
                        </SectionFormulaire>
                        : null
                    }
                    {dre.enseignant
                        ? <SectionFormulaire title={'INFORMATIONS ENSEIGNANT'}>
                            <TableInfosEnseignant enseignant={dre.enseignant}/>
                        </SectionFormulaire>
                        : null
                    }

                    <SectionFormulaire title={'INFORMATIONS DEMANDE'}>
                        <TableInfosDemande dre={dre} />
                    </SectionFormulaire>

                    {type === "enseignant"
                        ? null
                        : <SectionFormulaire title={'DETAILS'}>
                            <TableDetailsAbsence dre={dre}/>
                        </SectionFormulaire>
                    }

                    {dre.statutCourant === "PLANIFIEE"
                        ? <SectionFormulaire title={'PLANIFICATION DE LA REPRISE'}>
                            <PlanificationDetails reprise={dre.coursGroupe.reprise}/>
                        </SectionFormulaire>
                        : null
                    }

                    {type === "personnel"
                        ? <HistoriqueEtudiant idEtudiant={dre.etudiant.id}/>
                        : null
                    }

                    {dre.listeMessage
                        ? <MessagesDRE
                            messages={dre.listeMessage}
                            typeUtilisateur={type}
                            idDRE={idDRE}
                            actualiserDRE={checherDRE}
                        />
                        : null
                    }


                    <Container maxWidth={false}>
                        <div style={{margin: "1.25rem"}}>
                            <Stack direction="row" spacing={1} justifyContent="space-between">

                                <Button
                                    variant="contained"
                                    component={Link}
                                    startIcon={<KeyboardReturnIcon/>}
                                    to="/"
                                    color="primary"
                                > Retour
                                </Button>

                                <Stack direction="row" spacing={1}>
                                    <Button
                                        onClick={handleAnnulerDRE}
                                        variant="contained"
                                        color="error"
                                        disabled={dre.statutCourant === "ANNULEE"}
                                    > Annuler la demande
                                    </Button>

                                    <Dialog
                                        open={confirmationDialogOpen}
                                        onClose={() => setConfirmationDialogOpen(false)}
                                        fullWidth
                                    >
                                        <DialogTitle>{confirmationDialogTitre}</DialogTitle>
                                        <DialogContent>
                                            <DialogContentText>{confirmationDialogContenu}</DialogContentText>
                                        </DialogContent>
                                        <DialogActions>
                                            <Button autoFocus onClick={() => setConfirmationDialogOpen(false)}>
                                                Annuler
                                            </Button>
                                            <Button onClick={annulerDemande}>Oui, je confirme</Button>
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

                                    <Stack direction="row" spacing={1}>
                                        {juge &&
                                            <JugerDRE
                                                idDRE={idDRE}
                                                juge={juge}
                                                decisionCourante={dre.decisionCourante.typeDecision}
                                                statutCourant={dre.statutCourant}
                                                actualiserDRE={checherDRE}
                                            />
                                        }
                                    </Stack>
                                </Stack>

                            </Stack>
                        </div>
                    </Container>
                </>
            }
        </MiseEnPage>
    )
}