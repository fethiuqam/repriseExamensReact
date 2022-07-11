import React, {useContext} from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Button, Chip, ListItemText} from "@mui/material";
import LoupeRoundedIcon from '@mui/icons-material/LoupeRounded';
import AuthContext from "../../context/AuthProvider";
import {format} from "date-fns";
import locale from "date-fns/locale/fr-CA"

const FORMAT_DATE = 'dd MMMM yyyy';

export default function LigneDRE({item}) {

    const {role} = useContext(AuthContext);

    return (
        <TableRow key={item.id}>
            <TableCell>{format(new Date(item.dateHeureSoumission), FORMAT_DATE, {locale})}</TableCell>
            {role === "etudiant"
                ? null
                : <TableCell>
                    <ListItemText primary={item.nomEtudiant} secondary={item.codePermanentEtudiant}/>
                </TableCell>
            }
            {role === "enseignant"
                ? null
                : <TableCell>
                    <ListItemText
                        primary={item.nomEnseignant}
                        secondary={role === 'commis' && item.matriculeEnseignant}/>
                </TableCell>
            }
            <TableCell>{item.sigleCours} - {item.groupe}</TableCell>
            <TableCell>{item.session}</TableCell>
            <TableCell>
                <Chip label={item.statut}/>
            </TableCell>
            {role === "commis" &&
                <TableCell>
                    <Chip label={item.decision ? item.decision : "Aucune"}/>
                </TableCell>
            }
            <TableCell>
                <Button
                    size="small"
                    variant="contained"
                    endIcon={<LoupeRoundedIcon/>}
                    href={`/demandes/${item.id}`}
                >
                    Consulter
                </Button>
            </TableCell>
        </TableRow>
    );
}