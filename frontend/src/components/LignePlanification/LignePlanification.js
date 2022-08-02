import React, {useState} from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    ListItemText
} from "@mui/material";
import CancelIcon from '@mui/icons-material/Cancel';
import EditIcon from '@mui/icons-material/Edit';
import EventNoteIcon from '@mui/icons-material/EventNote';
import {afficherSession} from "../../utils/utils";

export default function LignePlanification({item, modifierPlanification, annulerPlanification}) {

    const [confirmationDialogOpen, setConfirmationDialogOpen] = useState(false);

    const handleAnnulerPlanification = () => {
        setConfirmationDialogOpen(true);
    }

    return (
        <>
            <TableRow key={item.id}>

                <TableCell>{item.cours.sigle}-{item.groupe}</TableCell>

                <TableCell>{afficherSession(item)}</TableCell>

                <TableCell>
                    <ListItemText
                        primary={`${item.enseignant.prenom} ${item.enseignant.nom}`}
                        secondary={item.enseignant.matricule}/>
                </TableCell>

                <TableCell>
                    <Button
                        sx={{marginRight: "5px"}}
                        size="small"
                        variant="contained"
                        color={item.reprise ? "primary" : "success"}
                        endIcon={item.reprise ? <EditIcon/> : <EventNoteIcon/>}
                        onClick={() => modifierPlanification(item)}
                    >
                        {item.reprise ? "Modifier" : "Planifier"}
                    </Button>
                    {item.reprise
                        && <Button
                            sx={{marginRight: "5px"}}
                            size="small"
                            variant="contained"
                            color="error"
                            endIcon={<CancelIcon/>}
                            onClick={handleAnnulerPlanification}
                        >
                            Annuler
                        </Button>
                    }

                </TableCell>

            </TableRow>

            <Dialog
                open={confirmationDialogOpen}
                onClose={() => setConfirmationDialogOpen(false)}
                fullWidth
            >
                <DialogTitle>Confirmer l'annulation de la planification</DialogTitle>
                <DialogContent>
                    <DialogContentText>Êtes vous sûr de vouloir annuler la planification de la reprise
                        d'examen?</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button autoFocus onClick={() => setConfirmationDialogOpen(false)}>
                        Annuler
                    </Button>
                    <Button onClick={() => {
                        annulerPlanification(item);
                        setConfirmationDialogOpen(false)
                    }}>Oui, je confirme</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}

