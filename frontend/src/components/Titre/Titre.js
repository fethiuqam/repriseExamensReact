import { Container, Typography } from '@material-ui/core';
import * as React from 'react';
import '../../styles/StyleEtudiant.css'

const Titre = (props) => {
    return (
        <Container>
            <Typography variant='h4' className='titre'>{props.titre}</Typography>
        </Container>
    )
}

export default Titre;