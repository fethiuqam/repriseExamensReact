import React from "react";
import { Container , Paper } from '@material-ui/core';
import '../../styles/StyleEtudiant.css'

function SectionFormulaire(props) {

    return (
        <Container maxWidth={false}>
            <Paper variant="outlined" square className="sectionFormulaire">
                <div className="sectionFormulaireDivTitre">
                    <h3 className="sectionFormulaireTitre">{props.title}</h3>
                </div>
                <div className="sectionFormulaireDivContenu">
                   {props.children}
                </div>
            </Paper>
        </Container>
    );
}

export default SectionFormulaire