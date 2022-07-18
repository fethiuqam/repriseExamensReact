import { Typography } from '@material-ui/core';
import * as React from 'react';
import '../../styles/StyleEtudiant.css'

const Titre = (props) => {
    return (
        <div className="divTitre">
            <Typography variant='h4' className='titre'>{props.titre}</Typography>
        </div>
    )
}

export default Titre;