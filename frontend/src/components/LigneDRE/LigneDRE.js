import React, {useContext} from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Badge, Button, ListItemText} from "@mui/material";
import LoupeRoundedIcon from '@mui/icons-material/LoupeRounded';
import AuthContext from "../../context/AuthProvider";
import {Link} from "react-router-dom";
import {afficherDate} from "../../utils/utils";
import Statut from "../Statut/Statut";
import Decision from "../Decision/Decision";

function LigneDRE({item}) {

    const {type} = useContext(AuthContext);

    const session = item.coursGroupe.session.substring(0,3) + "-" + item.coursGroupe.annee.substring(2);

    return (
        <TableRow key={item.id}>
            <TableCell>{afficherDate(item.dateHeureSoumission)}</TableCell>
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
            <TableCell>{item.coursGroupe.cours.sigle}-{item.coursGroupe.groupe}</TableCell>
            <TableCell>{session}</TableCell>

            <TableCell>
                <Statut statut={item.statutCourant} />
            </TableCell>
            {type === "personnel" &&
                <TableCell>
                    <Decision decision={item.decisionCourante} />
                </TableCell>
            }
            <TableCell>
                <Badge color="error" invisible={true} badgeContent={<h3>!</h3>}>
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