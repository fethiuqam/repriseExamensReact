import React, {useContext} from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import {styled} from '@mui/material/styles';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import {Link} from "react-router-dom";
import {Navigate} from "react-router";
import {Stack} from "@mui/material";
import '../../styles/StyleEtudiant.css'
import AuthContext from "../../context/AuthProvider";

const entete = 'UQÀM | Demandes de reprises d\'examens'
const deconnexion = 'Déconnexion'

const StyledTabs = styled((props) => (
    <Tabs
        {...props}
        TabIndicatorProps={{children: <span className="MuiTabs-indicatorSpan"/>}}
    />
))({
    '& .MuiTabs-indicator': {
        display: 'flex',
        justifyContent: 'center',
        backgroundColor: 'transparent',
    },
    '& .MuiTabs-indicatorSpan': {
        maxWidth: 40,
        width: '100%',
        backgroundColor: '#fff',
    },
});

const StyledTab = styled((props) => (
    <Tab disableRipple {...props} />
))(() => ({
    color: 'rgba(255, 255, 255, 0.7)',
    '&.Mui-selected': {
        color: '#fff',
    }
}));

const Entete = () => {

    const {setAuth, setType, setId, setJwt, auth, type} = useContext(AuthContext);

    const [value, setValue] = React.useState(0);

    const handleChange = (_, newValue) => {
        setValue(newValue);
    };

    const deconnecter = () => {
        setAuth(false);
        setType(null);
        setId(null);
        setJwt(null);
        return <Navigate to="/"/>;
    }

    return (
        <AppBar position="static">
            <Toolbar className='entete'>
                    <Typography variant="h5" component="div" className="enteteTitre">
                        {entete}
                    </Typography>
                    {auth &&
                        <Stack direction="row" spacing={4} justifyContent="space-between">
                            <Box>
                                <StyledTabs
                                    value={value}
                                    onChange={handleChange}
                                    aria-label="styled tabs example"
                                >
                                    <StyledTab label="Consulter les demandes" to="/" component={Link}/>

                                    {type === "etudiant" &&
                                        <StyledTab label="Faire une demande" to="/faire-demande" component={Link}/>
                                    }
                                </StyledTabs>
                            </Box>

                            <Button color='inherit' onClick={deconnecter}>
                                {deconnexion}
                            </Button>
                        </Stack>
                    }
            </Toolbar>
        </AppBar>
    );
}

export default Entete;
