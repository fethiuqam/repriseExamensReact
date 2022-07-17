import React, {useContext} from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Badge, Button, Chip, ListItemText} from "@mui/material";
import LoupeRoundedIcon from '@mui/icons-material/LoupeRounded';
import AuthContext from "../../context/AuthProvider";
import {format} from "date-fns";
import locale from "date-fns/locale/fr-CA"
import { Link } from "react-router-dom";

const FORMAT_DATE = 'dd MMMM yyyy';

function LigneDRE({item}) {

    const {type} = useContext(AuthContext);

    return (
        <TableRow key={item.id}>
            <TableCell>{format(new Date(item.dateHeureSoumission), FORMAT_DATE, {locale})}</TableCell>
            {type === "etudiant"
                ? null
                : <TableCell>
                    <ListItemText primary={`${item.etudiant.prenom} ${item.etudiant.nom}`}
                                  secondary={item.etudiant.codePermanent}/>
                </TableCell>
            }
            {type === "enseignant"
                ? null
                : <TableCell>
                    <ListItemText
                        primary={`${item.enseignant.prenom} ${item.enseignant.nom}`}
                        secondary={type === 'personnel' && item.enseignant.matricule}/>
                </TableCell>
            }
            <TableCell>{item.coursGroupe.cours.sigle} - {item.coursGroupe.groupe}</TableCell>
            <TableCell>{item.coursGroupe.session}</TableCell>
            <TableCell>
                <Chip label={item.statutCourant}/>
            </TableCell>
            {type === "personnel" &&
                <TableCell>
                    <Chip label={item.decisionCourante ? item.decisionCourante : "Aucune"}/>
                </TableCell>
            }
            <TableCell>
                <Badge color="error" invisible={true} badgeContent={<h3>!</h3>} >
                    <Button
                        size="small"
                        variant="contained"
                        component={Link}
                        endIcon={<LoupeRoundedIcon/>}
                        to={`/details/${item.id}`}
                    >
                        Consulter
                    </Button>
                </Badge>
            </TableCell>
        </TableRow>
    );
}

export default LigneDRE;