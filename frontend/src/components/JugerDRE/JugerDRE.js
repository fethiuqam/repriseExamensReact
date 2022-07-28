import React, {useState} from "react";
import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Snackbar,
    TextField
} from "@mui/material";
import DoneIcon from "@mui/icons-material/Done";
import ClearIcon from '@mui/icons-material/Clear';
import CancelScheduleSendIcon from '@mui/icons-material/CancelScheduleSend';

const JugerDRE = ({idDRE, juge, decisionCourante, statutCourant, actualiserDRE}) => {

    const [details, setDetails] = useState("");
    const [jugement, setJugement] = useState("");

    const [detailsDialogOpen, setDetailsDialogOpen] = useState(false);
    const [detailsDialogTitre, setDetailsDialogTitre] = useState("");
    const [detailsDialogContenu, setDetailsDialogContenu] = useState("");
    const [detailsDialogTexteBouton, setDetailsDialogTexteBouton] = useState("");
    const [dialogContentDetailsVisible, setDialogContentDetailsVisible] = useState(true);

    const [confirmationDialogOpen, setConfirmationDialogOpen] = useState(false);
    const [confirmationDialogTitre, setConfirmationDialogTitre] = useState("");
    const [confirmationDialogContenu, setConfirmationDialogContenu] = useState("");

    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarSeverity, setSnackBarSeverity] = useState("success");
    const [snackBarTexte, setSnackBarTexte] = useState("");
    const [snackBarTexteSucces, setSnackBarTexteSucces] = useState("");
    const [snackBarTexteError, setSnackBarTexteError] = useState("");

    const handleAccepterDemande = () => {
        setJugement("accepter")
        setDetailsDialogTitre("Accepter la demande");
        setDetailsDialogContenu("Veuillez saisir les détails de l'acceptation.");
        setDetailsDialogTexteBouton("Accepter la demande");
        setDialogContentDetailsVisible(true);
        setConfirmationDialogTitre("Confirmer l'acceptation de la demande");
        setConfirmationDialogContenu("Êtes vous sûr de vouloir accepter la demande?");
        setSnackBarTexteSucces("La demande a été acceptée avec succès.");
        setSnackBarTexteError("Un problème est survenu lors de l'acceptation de la demande. Veuillez réessayer.")
        setDetailsDialogOpen(true);
    }

    const handleRejeterDemande = () => {
        setJugement("rejeter")
        setDetailsDialogTitre("Rejeter la demande");
        setDetailsDialogContenu("Veuillez saisir les détails du rejet.");
        setDetailsDialogTexteBouton("Rejeter la demande");
        setDialogContentDetailsVisible(true);
        setConfirmationDialogTitre("Confirmer le rejet de la demande");
        setConfirmationDialogContenu("Êtes vous sûr de vouloir rejeter la demande?");
        setSnackBarTexteSucces("La demande a été rejetée avec succès.");
        setSnackBarTexteError("Un problème est survenu lors du rejet de la demande. Veuillez réessayer.")
        setDetailsDialogOpen(true);
    }

    const handleAnnulerRejet = () => {
        setJugement("annuler-rejet")
        setDetailsDialogTitre("Annuler le rejet");
        setDetailsDialogContenu("Veuillez saisir les détails de l'annulation du rejet.");
        setDetailsDialogTexteBouton("Annuler le rejet");
        setDialogContentDetailsVisible(false);
        setConfirmationDialogTitre("Confirmer l'annulation du rejet de la demande");
        setConfirmationDialogContenu("Êtes vous sûr de vouloir annuler le rejet?");
        setSnackBarTexteSucces("Le rejet a été annulé avec succès.");
        setSnackBarTexteError("Un problème est survenu lors de l'annulation du rejet. Veuillez réessayer.")
        setDetailsDialogOpen(true);
    }

    const jugerDemande = () => {
        setSnackBarOpen(true);
        const API_URL = `/api/demandes/${idDRE}/${jugement}`;
        const patch = {details: details};
        const accepterDemandeCommis = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: 'PATCH',
                        body: JSON.stringify(patch),
                        headers: {
                            'Content-type': 'application/json; charset=UTF-8'
                        },
                        credentials: 'include'
                    });
                if (reponse.status === 406) {
                    setSnackBarSeverity("error");
                    setSnackBarTexte("Cette action n'est pas permise.");
                    setSnackBarOpen(true);
                } else if (!reponse.ok) throw Error();
                else {
                    setSnackBarSeverity("success");
                    setSnackBarTexte(snackBarTexteSucces);
                    setSnackBarOpen(true);
                    actualiserDRE();
                }
            } catch (err) {
                setSnackBarSeverity("error");
                setSnackBarTexte(snackBarTexteError);
                setSnackBarOpen(true);
            }
        }
        accepterDemandeCommis();
        setDetails("");
        setConfirmationDialogOpen(false);
    }

    const desactiverAccepterRejeterdemande = () => {
        if (statutCourant === "ANNULEE" || statutCourant === "ENREGISTREE") {
            return true;
        } else if (juge === "commis") {
            return decisionCourante !== "AUCUNE";
        } else if (juge === "directeur") {
            return decisionCourante !== "AUCUNE"
                && decisionCourante !== "ACCEPTATION_RECOMMANDEE"
                && decisionCourante !== "REJET_RECOMMANDE";
        } else if (juge === "enseignant") {
            return decisionCourante !== "ACCEPTEE_DIRECTEUR";
        }
        return false;
    }

    const afficherAnnulerRejet = () => {
        if (juge === "commis") {
            return decisionCourante === "REJET_RECOMMANDE";
        } else if (juge === "directeur") {
            return decisionCourante === "REJETEE_DIRECTEUR";
        } else if (juge === "enseignant") {
            return decisionCourante === "REJETEE_ENSEIGNANT";
        }
        return false;
    }

    return (
        <>
            <Button
                onClick={handleAccepterDemande}
                variant="contained"
                color="success"
                endIcon={<DoneIcon/>}
                disabled={desactiverAccepterRejeterdemande()}
            >
                {juge === "commis" ? "Recommander l'acceptation" : "Accepter la demande"}
            </Button>
            {afficherAnnulerRejet()
                ? <Button
                    onClick={handleAnnulerRejet}
                    variant="contained"
                    color="warning"
                    endIcon={<CancelScheduleSendIcon/>}
                >
                    Annuler le rejet
                </Button>
                : <Button
                    onClick={handleRejeterDemande}
                    variant="contained"
                    color="error"
                    endIcon={<ClearIcon/>}
                    disabled={desactiverAccepterRejeterdemande()}
                >
                    {juge === "commis" ? "Recommander le rejet" : "Rejeter la demande"}
                </Button>
            }

            <Dialog
                open={detailsDialogOpen}
                onClose={() => setDetailsDialogOpen(false)}
                fullWidth
            >
                <DialogTitle>{detailsDialogTitre}</DialogTitle>
                {dialogContentDetailsVisible
                    ? <DialogContent>
                        <DialogContentText>{detailsDialogContenu}</DialogContentText>
                        <TextField
                            label="Détails"
                            variant="outlined"
                            multiline
                            rows={3}
                            autoFocus
                            margin="dense"
                            fullWidth
                            value={details}
                            onChange={e => setDetails(e.target.value)}
                        />
                    </DialogContent>
                    : null
                }

                <DialogActions>
                    <Button onClick={() => setDetailsDialogOpen(false)}>Annuler</Button>
                    <Button
                        onClick={() => {
                            setDetailsDialogOpen(false);
                            setConfirmationDialogOpen(true);
                        }}
                        disabled={ jugement === "rejeter" && details.trim().length < 3}
                    >{detailsDialogTexteBouton}
                    </Button>
                </DialogActions>
            </Dialog>

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
                    <Button onClick={jugerDemande}>Oui, je confirme</Button>
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

export default JugerDRE;