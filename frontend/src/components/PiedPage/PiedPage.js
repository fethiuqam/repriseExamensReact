import { Typography } from "@material-ui/core";
import React from "react";
import '../../styles/StyleEtudiant.css';

const universite = 'Université du Québec à Montréal ';
const bigl = ' BIGL'

const PiedPage = () => {
    return (
        <footer>
            <Typography>{universite}|{bigl}</Typography>
        </footer>

    );
}

export default PiedPage;