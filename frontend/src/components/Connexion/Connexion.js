import React, {useContext, useState} from "react";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useNavigate} from 'react-router-dom';
import AuthContext from "../../context/AuthProvider";
import {connectionUtilisateur} from "../../api/AuthentificationService";


export default function Connexion() {

    const {setAuth, setType, setId, setPermissions} = useContext(AuthContext);

    const [valAuth, setValAuth] = useState({
        codeMs: '',
        motDePasse: ''
    });

    const [errConnectMessage, setErrConnectMessage] = useState('');

    const navigate = useNavigate();
    const handleSubmit = (event) => {
        event.preventDefault();

        connectionUtilisateur(valAuth).then((response)=>{
             setAuth(true);
             setType(response.type);
             setId(response.id);
             setPermissions(response.permissions);
             sessionStorage.setItem("Id", response.id);
             sessionStorage.setItem("Type", response.type);
             sessionStorage.setItem("Permissions",response.permissions);
             navigate("/", {replace: true});
        }).catch(()=>{
            setErrConnectMessage("Échec, mauvaises informations d'identification");
        });
    };

    const handleChange = (val) => {
        val.persist();
        setValAuth(valAuth => ({
            ...valAuth,
            [val.target.name]: val.target.value
        }));
    };

    return (
        <>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Connexion
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="codeMs"
                            label="Code MS"
                            name="codeMs"
                            autoFocus
                            value={valAuth.codeMs}
                            onChange={handleChange}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="motDePasse"
                            label="Mot de passe"
                            type="password"
                            id="motDePasse"
                            value={valAuth.motDePasse}
                            onChange={handleChange}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Se connecter
                        </Button>
                    </Box>
                    {errConnectMessage && (
                        <Box sx={{display: 'flex', justifyContent: 'center'}}>
                            <h4 style={{color: 'red'}}>{errConnectMessage}</h4>
                        </Box>
                    )}
                </Box>
            </Container>
        </>
    )
        ;
}
