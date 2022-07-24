import React, { useState, useEffect, useContext } from 'react';
import AuthContext from "../../context/AuthProvider";
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import Box from "@mui/material/Box";
import {CircularProgress} from "@mui/material";
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody';
import TableHead from '@mui/material/TableHead';
import { Typography } from "@mui/material";
import { Button } from "@mui/material";
import {useNavigate} from "react-router-dom";

const ListerRole = () => {

    const API_URL = `/api/roles`;
    const {id, jwt} = useContext(AuthContext);
    const navigate = useNavigate();

    const [listeRoles, setListeRoles] = useState([]);
    const [fetchError, setFetchError] = useState(null);
    const [enChargement, setEnChargement] = useState(true);

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: 'get',
                        headers: { 'Authorization': 'Bearer ' + jwt }
                    });
                if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
                const listRoles = await reponse.json();
                setListeRoles(listRoles._embedded.roles);
                setFetchError(null);
            } catch (err) {
                setFetchError(err.message);
            } finally {
                setEnChargement(false);
            }
        }
        fetchItems();
    }, [id, jwt]);

    const handleDeleteClick = (rolesId) => {
        const supprimerRoles = async () => {
            try {
                const reponse = await fetch(`/api/roles/${rolesId}`,
                    {
                        method: 'delete',
                        headers: { 'Authorization': 'Bearer ' + jwt }

                    });
                if (!reponse.ok) throw Error('Un problème est survenu lors de la suppression du role.');
                setFetchError(null);
            } catch (err) {
                setFetchError(err.message);
            } finally {
                setEnChargement(false);
            }
        }
        const newRoles = [...listeRoles];
        const index = listeRoles.findIndex((roles) => roles.id === rolesId);
        newRoles.splice(index, 1);
        setListeRoles(newRoles);
        supprimerRoles();
    }

    const routeChangeVoirRole = (id) =>{
        let path = `/voir-roles/${id}`;
        navigate(path);
    }
    const routeChangeCreerRole = () =>{
        let path = `/new-roles`;
        navigate(path);
    }
    const routeChangeUpdateRole = (id) =>{
        let path = `/update-roles/${id}`;
        navigate(path);
    }

    return (
        <MiseEnPage>
            <SectionFormulaire
                title={'LISTE ROLES'}>
                <Button
                    size="small"
                    variant="contained"
                    onClick={() => routeChangeCreerRole()}
                >
                    Ajouter
                </Button>
                {enChargement
                    ? <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                        <CircularProgress />
                    </Box>
                    : fetchError
                        ? <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                            <h3 style={{ color: 'red' }}>{fetchError}</h3>
                        </Box>
                        : Object.keys(listeRoles).length > 0
                            ?
                            <Table className="tableDRE-style" style={{ marginTop: '10px', marginBottom: 'auto' }}>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>
                                            <Typography noWrap sx={{fontWeight: "bold"}}>
                                                ID
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Typography noWrap sx={{fontWeight: "bold"}}>
                                                NOM
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Typography noWrap sx={{fontWeight: "bold"}}>
                                                Options
                                            </Typography>
                                        </TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {listeRoles.map((roles) => (
                                        <TableRow key={roles.id} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                            <TableCell>{roles.id}</TableCell>
                                            <TableCell>{roles.nom}</TableCell>
                                            <TableCell>
                                                <Box display="flex" justify='space-between'>
                                                    <Button
                                                        size="small"
                                                        variant="outlined"
                                                        onClick={() => routeChangeUpdateRole(roles.id)}
                                                    >
                                                        Modifier
                                                    </Button>

                                                    <Button
                                                        style={{ backgroundColor: "red", }}
                                                        size="small"
                                                        variant="contained"
                                                        onClick={() => handleDeleteClick(roles.id)}
                                                    >
                                                        Supprimer
                                                    </Button>

                                                    <Button
                                                        size="small"
                                                        variant="outlined"
                                                        onClick={() => routeChangeVoirRole(roles.id)}
                                                    >
                                                        Voir
                                                    </Button>

                                                </Box>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>

                            : <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                                <h3>Aucun role à afficher.</h3>
                            </Box>
                }
            </SectionFormulaire>
        </MiseEnPage>
    );
}

export default ListerRole;

