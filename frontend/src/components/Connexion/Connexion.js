import {useContext} from "react";
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useNavigate, useLocation} from 'react-router-dom';
import AuthContext from "../../context/AuthProvider";


export default function Connexion() {

    const {setAuth, setRole, setId} = useContext(AuthContext);

    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);

        if (data.get('user') === 'commis') {
            setAuth(true);
            setRole("commis");
        } else if (data.get('user') === 'directeur') {
            setAuth(true);
            setRole("directeur");
        } else if (data.get('user') === 'etudiant') {
            setAuth(true);
            setRole("etudiant");
            setId(1);
        } else if (data.get('user') === 'enseignant') {
            setAuth(true);
            setRole("enseignant");
            setId(1);
        } else {
            setAuth(false);
            setRole(null);
            setId(null);
        }
        navigate(from, {replace: true});
    };

    return (
        <>
            <p>Utilisateurs pour tester : commis - etudiant - enseignant</p>
            <p>Le id de l'etudiant et l'enseignant : 1</p>
            <p>Le mot de passe est facultatif</p>

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
                            id="user"
                            label="Code MS"
                            name="user"
                            autoFocus
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Mot de passe"
                            type="password"
                            id="password"
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
                </Box>
            </Container>
        </>
    )
        ;
}