import {STATUT_AFFICHAGE} from "../../utils/const";
import {Chip} from "@mui/material";
import React from "react";


const Statut = ({statut}) => {

    return (
        <Chip
            sx={{
                backgroundColor: STATUT_AFFICHAGE[statut][1],
                color:STATUT_AFFICHAGE[statut][2]
            }}
            label={STATUT_AFFICHAGE[statut][0]}
        />
    );

}

export default Statut;