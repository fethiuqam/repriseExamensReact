import '../../styles/StyleEtudiant.css'
import {MOTIF_AFFICHAGE} from "../../utils/const";
import { 
    Link, 
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Snackbar,
    Alert
} from "@mui/material";
import Box from '@mui/material/Box';
import { FileUploader } from "react-drag-drop-files";
import { useState } from "react";
import AttachmentIcon from '@mui/icons-material/Attachment';
import DeleteIcon from '@mui/icons-material/Delete';
import Button from '../BoutonFormulaire/BoutonFormulaire';

export default function TableDetailsAbsence(props) {

    const {dre} = props;
    const typeUtilisateur = props.typeUtilisateur;

    // gestion des pièces justificatives enregistrées dans la BD
    const [files, setFiles] = useState(dre.justifications);
    const [idPieceASupprimer, setIdPieceASupprimer] = useState(null);

    const [suppressionConfirmationDialogOpen, setSuppressionConfirmationDialogOpen] = useState(false);
    const [suppressionConfirmationDialogTitre, setSuppressionConfirmationDialogTitre] = useState("");
    const [suppressionConfirmationDialogContenu, setSuppressionConfirmationDialogContenu] = useState("");
    const [suppressionSnackBarOpen, setSuppressionSnackBarOpen] = useState(false);
    const [suppressionSnackBarSeverity, setSuppressionSnackBarSeverity] = useState("success");
    const [suppressionSnackBarTexte, setSuppressionSnackBarTexte] = useState("");
    const [suppressionSnackBarTexteSucces, setSuppressionSnackBarTexteSucces] = useState("");
    const [suppressionSnackBarTexteError, setSuppressionSnackBarTexteError] = useState("");

    const [ajoutConfirmationDialogOpen, setAjoutConfirmationDialogOpen] = useState(false);
    const [ajoutConfirmationDialogTitre, setAjoutConfirmationDialogTitre] = useState("");
    const [ajoutConfirmationDialogContenu, setAjoutConfirmationDialogContenu] = useState("");
    const [ajoutSnackBarOpen, setAjoutSnackBarOpen] = useState(false);
    const [ajoutSnackBarSeverity, setAjoutSnackBarSeverity] = useState("success");
    const [ajoutSnackBarTexte, setAjoutSnackBarTexte] = useState("");
    const [ajoutSnackBarTexteSucces, setAjoutSnackBarTexteSucces] = useState("");
    const [ajoutSnackBarTexteError, setAjoutSnackBarTexteError] = useState("");

    /**
     * Gère la mécanique de dialogue et de snackbar pour la suppression de pièce jointe
     * @param {*} fileId  id du fichier
     */
    const handleSupprimerPieceExistante = (fileId) => {
        setIdPieceASupprimer(fileId);
        setSuppressionConfirmationDialogOpen(true);
        setSuppressionConfirmationDialogTitre("Confirmer la suppression de la pièce");
        setSuppressionConfirmationDialogContenu("Êtes vous sûr de vouloir supprimer la pièce?");
        setSuppressionSnackBarTexteSucces("La pièce a été supprimée avec succès.");
        setSuppressionSnackBarTexteError("Un problème est survenu lors de la suppression de la pièce. Veuillez réessayer.")
    }

    /**
     * Supprime la pièce jointe visée
     */
    async function supprimerPieceExistante() {
        setSuppressionSnackBarOpen(true);
        const API_URL = `/api/justifications/${idPieceASupprimer}`; 
        const data = {idPieceASupprimer};

        const supprimerPiece = async () => {
            try {
                var response = await fetch(API_URL, {
                    method: "DELETE",
                    body: data,
                    credentials: 'include'
                });
    
                if (!response.ok) {
                    throw new Error("Erreur du POST");
                }

                setSuppressionSnackBarSeverity("success");
                setSuppressionSnackBarTexte(suppressionSnackBarTexteSucces);
                setSuppressionSnackBarOpen(true);
                actualiserListePieces();
            } catch (err) {
                setSuppressionSnackBarSeverity("error");
                setSuppressionSnackBarTexte(
                    err.cause === 406
                        ? "Cette action n'est pas permise."
                        : suppressionSnackBarTexteError
                );
                setSuppressionSnackBarOpen(true);
            }
        }
        supprimerPiece();
        setSuppressionConfirmationDialogOpen(false);
    }

    // gestion des nouvelles pièces justificatives
    const fileTypes = ["JPEG", "PNG", "PDF", "JPG"];
    const [pieceAAjouter, setPieceAAjouter] = useState();

    /**
     * Gère la mécanique de dialogue et de snackbar pour l'ajout d'une pièce justificative
     */
    const handleAjouterNouvellePiece = () => {
        setAjoutConfirmationDialogOpen(true);
        setAjoutConfirmationDialogTitre("Confirmer l'ajout de la pièce");
        setAjoutConfirmationDialogContenu("Êtes vous sûr de vouloir ajouter la pièce?");
        setAjoutSnackBarTexteSucces("La pièce a été ajoutée avec succès.");
        setAjoutSnackBarTexteError("Un problème est survenu lors de l'ajout de la pièce. Veuillez réessayer.")
    }

    /**
     * Ajoute la pièce jointe
     */
    const ajouterNouvellePiece = async () => {
        setAjoutSnackBarOpen(true);

        let formData = new FormData();
        formData.append('id', new Blob([JSON.stringify(dre.id)], { type: "application/json" }));
        formData.append("file", pieceAAjouter);

        const ajouterPiece = async () => {
            try {
                var response = await fetch("/api/justifications", {
                    method: "POST",
                    body: formData,
                    credentials: 'include'
                });
    
                if (!response.ok) {
                    throw new Error("Erreur du POST");
                }
                setAjoutSnackBarSeverity("success");
                setAjoutSnackBarTexte(ajoutSnackBarTexteSucces);
                setAjoutSnackBarOpen(true);
                actualiserListePieces();
            } catch (err) {
                setAjoutSnackBarSeverity("error");
                setAjoutSnackBarTexte(
                    err.cause === 406
                        ? "Cette action n'est pas permise."
                        : ajoutSnackBarTexteError
                );
                setAjoutSnackBarOpen(true);
            }
        }
        ajouterPiece();
        setAjoutConfirmationDialogOpen(false);
    }

    /**
     * Actualise la liste des pièces jointes lorsqu'il y a un ajout
     */
    const actualiserListePieces = async () => {
        const API_URL = `/api/demandes/${dre.id}`;
        try {
            const reponse = await fetch(API_URL,
                {
                    method: 'get',
                    credentials: 'include'
                });
            const data = await reponse.json();
            setFiles(data.justifications);
        } catch (err) {
            throw new Error("Erreur du POST");
        }
    }

    /**
     * Gère la pièce jointe une fois que l'utilisateur l'a glissée/déposée dans l'utilitaire
     * @param {*} nouveauFichier 
     */
    const handleChangeFile = (nouveauFichier) => {
        setPieceAAjouter(nouveauFichier);
        handleAjouterNouvellePiece();
    };
    
    /**
     * Crée la liste des pièces jointes
     * @param {*} pieces pièces jointes à la DRE
     * @returns 
     */
    function genererItemsListe(pieces) {
        return (
            <ul>
                {pieces.map((piece) => (
                <li key={piece.id}>
                  <Link
                    component="button"
                    variant="body1"
                    onClick={() =>
                        fetch(`/api/justifications/${piece.id}/preview`, {
                            credentials: 'include'
                      })
                        .then((r) => r.blob())
                        .then(
                          (blob) =>
                            (window.open(window.URL.createObjectURL(blob), "_blank"))
                        )
                    }
                  >
                    {piece.description ?? piece.nomFichier}
                  </Link>
                </li>
                ))}
            </ul>
        )
    }

    return (
        <div>
            <table className="affichageInfosTable">
                <tbody>
                <tr>
                    <th>Motif</th>
                    <td>{dre.motifAbsence != null ? MOTIF_AFFICHAGE[dre.motifAbsence] : ""}</td>
                </tr>
                {dre.absenceDetails
                    ? <tr>
                        <th>Détails motif</th>
                        <td> {dre.absenceDetails}</td>
                    </tr>
                    : null
                }
                </tbody>
            </table>
            {dre.justifications
                ? 
                <div style={{padding: '1rem'}}>
                    <h2>Pièces justificatives</h2>
                    {typeUtilisateur === 'etudiant' 
                        ? 
                        <div>
                            <div>
                                <ul className="afficherFichiers">
                                    {files.map(function (file, idx) {
                                        return (
                                            <li key={idx}>
                                                <AttachmentIcon></AttachmentIcon>
                                                {file.id 
                                                ? 
                                                <Link
                                                    component="button"
                                                    variant="body1"
                                                    onClick={() =>
                                                        fetch(`/api/justifications/${file.id}/preview`, {
                                                            credentials: 'include'
                                                    })
                                                        .then((r) => r.blob())
                                                        .then(
                                                        (blob) =>
                                                            (window.open(window.URL.createObjectURL(blob), "_blank"))
                                                        )
                                                    }
                                                >
                                                    {file.description ?? file.nomFichier}
                                                </Link>
                                                : 
                                                <span>{file.name}</span>
                                                }
                                                
                                                <span className='divSupprimerFichierButton'>
                                                    <Button
                                                        className="buttonSupprimerFichier"
                                                        startIcon={<DeleteIcon />}
                                                        disableElevation
                                                        color="error"
                                                        size="small"
                                                        onClick={() => {
                                                            handleSupprimerPieceExistante(file.id);
                                                        }}
                                                    >
                                                    </Button>
                                                    
                                                    <Dialog
                                                        open={suppressionConfirmationDialogOpen}
                                                        onClose={() => setSuppressionConfirmationDialogOpen(false)}
                                                        fullWidth
                                                    >
                                                        <DialogTitle>{suppressionConfirmationDialogTitre}</DialogTitle>
                                                        <DialogContent>
                                                            <DialogContentText>{suppressionConfirmationDialogContenu}</DialogContentText>
                                                        </DialogContent>
                                                        <DialogActions>
                                                            <Button autoFocus onClick={() => setSuppressionConfirmationDialogOpen(false)}>
                                                                Annuler
                                                            </Button>
                                                            <Button onClick={() => {
                                                                supprimerPieceExistante()
                                                            }}>Oui, je confirme</Button>
                                                        </DialogActions>
                                                    </Dialog>

                                                    <Snackbar
                                                        style={{width: "50%"}}
                                                        anchorOrigin={{vertical: "top", horizontal: "center"}}
                                                        open={suppressionSnackBarOpen}
                                                        autoHideDuration={5000}
                                                        onClose={() => setSuppressionSnackBarOpen(false)}
                                                    >
                                                        <Alert
                                                            onClose={() => setSuppressionSnackBarOpen(false)}
                                                            severity={suppressionSnackBarSeverity} sx={{width: '100%'}}
                                                        >
                                                            {suppressionSnackBarTexte}
                                                        </Alert>
                                                    </Snackbar>
                                                </span>
                                                <br />
                                            </li>)
                                    })}
                                </ul>
                            </div>
                            <div>
                                <FileUploader
                                    multiple={false}
                                    handleChange={handleChangeFile}
                                    onTypeError={(err) => console.log("Erreur type fichier")}
                                    name="file"
                                    types={fileTypes}
                                    label="Deposez vos fichiers ici"
                                    fileOrFiles={null}
                                    children={
                                        <Box component="div" sx={{
                                            width: 800,
                                            height: 200,
                                            p: 1,
                                            border: '1px dashed grey',
                                            '&:hover': {
                                                backgroundColor: 'primary.main',
                                                opacity: [0.6, 0.5, 0.4],
                                            },
                                        }}>
                                            <div className="center">
                                                <h3>Glisser les pièces jointes pour ajouter des preuves justificatives</h3>
                                            </div>
                                        </Box>
                                    }
                                />
                                
                                <Dialog
                                    open={ajoutConfirmationDialogOpen}
                                    onClose={() => setAjoutConfirmationDialogOpen(false)}
                                    fullWidth
                                >
                                    <DialogTitle>{ajoutConfirmationDialogTitre}</DialogTitle>
                                    <DialogContent>
                                        <DialogContentText>{ajoutConfirmationDialogContenu}</DialogContentText>
                                    </DialogContent>
                                    <DialogActions>
                                        <Button autoFocus onClick={() => setAjoutConfirmationDialogOpen(false)}>
                                            Annuler
                                        </Button>
                                        <Button onClick={ajouterNouvellePiece}>Oui, je confirme</Button>
                                    </DialogActions>
                                </Dialog>

                                <Snackbar
                                    style={{width: "50%"}}
                                    anchorOrigin={{vertical: "top", horizontal: "center"}}
                                    open={ajoutSnackBarOpen}
                                    autoHideDuration={5000}
                                    onClose={() => setAjoutSnackBarOpen(false)}
                                >
                                    <Alert
                                        onClose={() => setAjoutSnackBarOpen(false)}
                                        severity={ajoutSnackBarSeverity} sx={{width: '100%'}}
                                    >
                                        {ajoutSnackBarTexte}
                                    </Alert>
                                </Snackbar> 
                            </div>
                        </div>
                        : 
                        <ul>
                            {genererItemsListe(dre.justifications)}
                        </ul>
                    }
                    </div>
                : null
            }    
        </div>
    )
}
