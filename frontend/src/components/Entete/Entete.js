import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import '../../styles/StyleEtudiant.css'

const entete = 'UQÀM | Demandes de reprises d\'examens'
const deconnexion = 'Déconnexion'

const Entete = () => {
    return (
        <AppBar position="static">
            <Toolbar className='entete'>
                <Typography variant="h5" component="div" className="enteteTitre">
                    {entete}
                </Typography>
                <Button color='inherit'>
                    {deconnexion}
                </Button>
            </Toolbar>
        </AppBar>
    );
}

export default Entete;
