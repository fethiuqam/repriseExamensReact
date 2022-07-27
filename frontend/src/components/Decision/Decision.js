import {DECISION_AFFICHAGE} from "../../utils/const";
import {Chip} from "@mui/material";
import React from "react";


const Decision = ({decision}) => {

    return (
        <Chip
            color={decision ? DECISION_AFFICHAGE[decision][1] : "default"}
            label={decision ? DECISION_AFFICHAGE[decision][0] : "Aucune"}
        />
    );

}

export default Decision;