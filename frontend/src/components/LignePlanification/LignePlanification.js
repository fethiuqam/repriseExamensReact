import React from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Button, ListItemText} from "@mui/material";
import CancelIcon from '@mui/icons-material/Cancel';
import EditIcon from '@mui/icons-material/Edit';
import EventNoteIcon from '@mui/icons-material/EventNote';

export default function LignePlanification({item, modifierPlanification, annulerPlanification}) {

    const session = item.session.substring(0, 3) + "-" + item.annee.substring(2);

    return (
        <TableRow key={item.id}>

            <TableCell>{item.cours.sigle}-{item.groupe}</TableCell>

            <TableCell>{session}</TableCell>

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
                    ? <Button
                        sx={{marginRight: "5px"}}
                        size="small"
                        variant="contained"
                        color="error"
                        endIcon={<CancelIcon/>}
                        onClick={() => annulerPlanification(item)}
                    >
                        Annuler
                    </Button>
                    : null
                }

            </TableCell>

        </TableRow>
    );
}

