import React, {useContext} from 'react';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import {Badge, Button, Checkbox, ListItemText, Stack} from "@mui/material";
import LoupeRoundedIcon from '@mui/icons-material/LoupeRounded';
import AuthContext from "../../context/AuthProvider";
import {Link} from "react-router-dom";
import {afficherCoursGroupe, afficherDate, afficherSession, estArchivable} from "../../utils/utils";
import Statut from "../Statut/Statut";
import Decision from "../Decision/Decision";
import ArchiveIcon from "@mui/icons-material/Archive";
import {TypeId} from "../../utils/const";

function LigneDRE({item , selected, selectionnerItemAArchiver}) {

    const {type, permissions} = useContext(AuthContext);

    const handleSelect = (e) => {
        selectionnerItemAArchiver (e, item.id);
    }

    return (
        <TableRow key={item.id}>
            {permissions.includes("ArchiverDemande")
                && <TableCell>

                    <Stack direction="row">
                        <Checkbox
                            color="primary"
                            disabled={!estArchivable(item)}
                            checked={selected}
                            onChange={handleSelect}
                        />
                        < ArchiveIcon sx={{padding:"10px", color:!estArchivable(item)?"#AAA": "#000"}} />
                    </Stack>
                </TableCell>
            }
            <TableCell>{afficherDate(item.dateHeureSoumission)}</TableCell>
            {type !== TypeId.Etudiant
                && <TableCell>
                    <ListItemText primary={`${item.etudiant.prenom} ${item.etudiant.nom}`}
                                  secondary={item.etudiant.codePermanent}/>
                </TableCell>
            }
            {type === TypeId.Enseignant
                && <TableCell>
                    <ListItemText
                        primary={`${item.enseignant.prenom} ${item.enseignant.nom}`}
                        secondary={type === 'personnel' && item.enseignant.matricule}/>
                </TableCell>
            }
            <TableCell>{afficherCoursGroupe(item.coursGroupe)}</TableCell>
            <TableCell>{afficherSession(item.coursGroupe)}</TableCell>

            <TableCell>
                <Statut statut={item.statutCourant}/>
            </TableCell>
            {type === TypeId.Personnel
                && <TableCell>
                    <Decision decision={item.decisionCourante}/>
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