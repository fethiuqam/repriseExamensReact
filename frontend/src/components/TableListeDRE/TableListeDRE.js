import React, {useContext} from 'react';
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import {Button, Checkbox, Stack, TableSortLabel, Typography} from "@mui/material";
import ArchiveIcon from '@mui/icons-material/Archive';
import LigneDRE from "../LigneDRE/LigneDRE";
import AuthContext from "../../context/AuthProvider";
import {estArchivable} from "../../utils/utils";
import {Permission} from "../../utils/const";


export default function TableListeDRE({items, colonnes, trier, AArchiver, selectionnerItemAArchiver,
                                          archiverTout, setConfirmationDialogOpen}) {

    const {permissions} = useContext(AuthContext);

    const onSortClick = index => () => {
        trier(index)
    };

    return (
        <Table className="tableDRE-style" style={{marginTop: '10px', marginBottom: 'auto'}}>
            <TableHead>

                <TableRow>
                    {permissions.includes(Permission.ArchiverDemande)
                        && <TableCell>
                            <Stack direction="row">
                                <Checkbox
                                    color="primary"
                                    indeterminate={AArchiver.length > 0 && AArchiver.length < items.filter(x => estArchivable(x)).length}
                                    checked={items.filter(x => estArchivable(x)).length === AArchiver.length && AArchiver.length > 0}
                                    onChange={archiverTout}
                                />
                                < ArchiveIcon sx={{padding:"10px"}} />
                            </Stack>
                            {AArchiver.length > 0
                                && <Button onClick={() => setConfirmationDialogOpen(true)}>Archiver la selection</Button>}
                        </TableCell>
                    }
                    {colonnes.map((colonne, index) => (
                        <TableCell key={index}>
                            <TableSortLabel
                                active={colonne.active}
                                direction={colonne.order}
                                onClick={onSortClick(index)}
                            >
                                <Typography noWrap sx={{fontWeight: "bold"}}>
                                    {colonne.name}
                                </Typography>
                            </TableSortLabel>
                        </TableCell>
                    ))}
                    <TableCell>
                        <Typography noWrap sx={{fontWeight: "bold"}}>
                            Détails
                        </Typography>
                    </TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {items
                    .map(item => {
                        return (
                            <LigneDRE
                                item={item}
                                key={item.id}
                                selected={AArchiver.includes(item.id)}
                                selectionnerItemAArchiver={selectionnerItemAArchiver}
                            />
                        );
                    })}
            </TableBody>
        </Table>
    );
}
