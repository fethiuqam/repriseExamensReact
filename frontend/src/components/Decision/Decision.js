
import {Chip} from "@mui/material";
import React from "react";
import {DECISION_AFFICHAGE} from "../../utils/const";


const Decision = ({decision}) => {

    return (
        <Chip
            color={decision ? DECISION_AFFICHAGE[decision?.typeDecision][1] : "default"}
            label={decision ? DECISION_AFFICHAGE[decision?.typeDecision][0] : "Aucune"}
        />
    );

}

export default Decision;