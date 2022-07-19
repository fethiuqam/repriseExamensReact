import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";
import React, {useContext, useState} from "react";
import Grid from "@mui/material/Grid";
import {
    Alert,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Divider,
    List,
    ListItem,
    ListItemText,
    Snackbar,
    TextField
} from "@mui/material";
import SendIcon from '@mui/icons-material/Send';
import AuthContext from "../../context/AuthProvider";
import {afficherDateHeure} from "../../utils/utils";
import {STYLE_MESSAGE_ENVOYE, STYLE_MESSAGE_RECU} from "../../utils/const";


const MessagesDRE = ({messages, typeUtilisateur, idDRE, actualiserDRE}) => {

    const {jwt} = useContext(AuthContext);

    const [message, setMessage] = useState("");
    const [messageDialogOpen, setmessageDialogOpen] = useState(false);

    const [snackBarOpen, setSnackBarOpen] = useState(false);
    const [snackBarSeverity, setSnackBarSeverity] = useState("success");
    const [snackBarTexte, setSnackBarTexte] = useState("");

    const envoyerMessage = () => {
        setSnackBarOpen(true);
        const API_URL = `/api/demandes/${idDRE}/messages`;
        const patch = {message: message};
        const envoyer = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: 'POST',
                        body: JSON.stringify(patch),
                        headers: {
                            'Authorization': 'Bearer ' + jwt,
                            'Content-type': 'application/json; charset=UTF-8'
                        }
                    });
                if (reponse.status === 401) {
                    setSnackBarSeverity("error");
                    setSnackBarTexte("Utilisateur non autorisé.");
                    setSnackBarOpen(true);
                } else if (!reponse.ok) throw Error();
                else {
                    setSnackBarSeverity("success");
                    setSnackBarTexte("Votre message a été envoyé avec succès.");
                    setSnackBarOpen(true);
                    actualiserDRE();
                }
            } catch (err) {
                setSnackBarSeverity("error");
                setSnackBarTexte(`"Une erreur est survenue lors de l'envoi de votre message. Veuillez réessayer."`);
                setSnackBarOpen(true);
            }
        }
        envoyer();
        setMessage("");
        setmessageDialogOpen(false);
    }

    const alignement = (typeMessage) => {
        if ((typeUtilisateur === "personnel" && typeMessage === "DEMANDE_COMMIS")
            || (typeUtilisateur === "etudiant" && typeMessage === "REPONSE_ETUDIANT"))
            return "left";
        else
            return "right";
    }

    return (
        <>
            <SectionFormulaire title={'Messages'}>
                {messages.length
                    ? <List>
                        {messages.map(message => {
                            return (
                                <ListItem key={message.id}>
                                    <Grid container>
                                        <Grid item xs={12} align={alignement(message.typeMessage)}>
                                            <ListItemText
                                                primary={message.contenu}
                                                style={alignement(message.typeMessage) === "left"
                                                    ? STYLE_MESSAGE_ENVOYE
                                                    : STYLE_MESSAGE_RECU
                                                }
                                            />
                                        </Grid>
                                        <Grid item xs={12} align={alignement(message.typeMessage)}>
                                            <ListItemText
                                                secondary={afficherDateHeure(message.dateHeure)}
                                            />
                                        </Grid>
                                    </Grid>
                                </ListItem>
                            )
                        })
                        }
                    </List>
                    :
                    <Grid item xs={12} align="center" style={{marginBottom: '2rem'}}>
                        <h3>Aucun Message à afficher.</h3>
                    </Grid>
                }

                <Divider/>
                <Grid item xs={12} align={alignement(message.typeMessage)}>
                    <Button
                        onClick={() => setmessageDialogOpen(true)}
                        variant="contained"
                        color="secondary"
                        endIcon={<SendIcon/>}
                        style={{marginTop: '1rem'}}
                    >Envoyer un message</Button>
                </Grid>

            </SectionFormulaire>

            <Dialog
                open={messageDialogOpen}
                onClose={() => setmessageDialogOpen(false)}
                fullWidth
            >
                <DialogTitle>Envoyer un message</DialogTitle>
                <DialogContent>
                    <DialogContentText>Veuiller saisir votre message.</DialogContentText>
                    <TextField
                        label="Détails"
                        variant="outlined"
                        multiline
                        rows={3}
                        autoFocus
                        margin="dense"
                        fullWidth
                        value={message}
                        onChange={e => setMessage(e.target.value)}
                    />
                </DialogContent>

                <DialogActions>
                    <Button onClick={() => setmessageDialogOpen(false)}>Annuler</Button>
                    <Button
                        onClick={envoyerMessage}
                        disabled={message.trim().length === 0}
                    >Envoyer</Button>
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
    )
}

export default MessagesDRE;