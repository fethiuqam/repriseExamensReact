import React from 'react';
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import {TableSortLabel, Typography} from "@mui/material";
import LignePlanification from "../LignePlanification/LignePlanification";


export default function TablePlanification({items, colonnes, trier, modifierPlanification, annulerPlanification}) {

    const onSortClick = index => () => {
        trier(index)
    };

    return (
        <Table className="tableDRE-style" style={{marginTop: '10px', marginBottom: 'auto'}}>
            <TableHead>
                <TableRow>
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
                            Actions
                        </Typography>
                    </TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {items
                    .map(item => {
                        return (
                            <LignePlanification
                                key={item.id}
                                item={item}
                                modifierPlanification={modifierPlanification}
                                annulerPlanification={annulerPlanification}
                            />
                        );
                    })}
            </TableBody>
        </Table>
    );
}
