import React, {useContext, useState} from "react";
import {useNavigate} from "react-router";
import {Button, Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import AuthContext from "../../context/AuthProvider";
import Box from "@mui/material/Box";

const CreerRole = () => {

        let navigate = useNavigate();
        const {jwt} = useContext(AuthContext);
        const [nom, setNom] = useState('');
        const [permissions, setPermissions] = useState([]);
        const [fetchError, setFetchError] = useState(null);


        const handleCreateClick = (event) => {
            event.preventDefault();
            let role = {nom, permissions};
            console.log('role => ' + JSON.stringify(role));

            const creerRole = async () => {
                try {
                    const reponse = await fetch('/roles' + (role.id ? '/' + role.id : ''),
                        {
                            method: 'post',
                            headers: {"Content-Type": "application/json", 'Authorization': 'Bearer ' + jwt},
                            body: JSON.stringify(role)
                        }).then(() => {
                            navigate('/roles');
                        }
                    );
                    if (!reponse.ok) throw Error('Un problème est survenu lors de la creation du role.');
                    setFetchError(null);
                } catch (err) {
                    setFetchError(err.message);
                }
            }
            creerRole();
        }


        const updateNom = (event) => {
            setNom(event.target.value)
        }

        const permissionsHandler = (event) => {
            setPermissions(perm => [event.target.value, ...perm])
        }


        const routeChangeCancel = () => {
            let path = `/roles`;
            navigate(path);
        }

        return (
            <div className="container center2 mt-5">
                <div className="row">

                    <div className="card col-md-6 offset-md-3 offset-md-3 center3">
                        <div className="card-body">
                            <form onSubmit={handleCreateClick}>
                                <div className="form-group">
                                    <label> Nom: </label>
                                    <input placeholder="Nom du role" name="nom" className="form-control"
                                           value={nom} onChange={updateNom}/>
                                </div>
                                {fetchError && <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                    <h3 style={{color: 'red'}}>{fetchError}</h3>
                                </Box>}
                                <div className="form-group">
                                    <label> Permissions: </label>
                                </div>
                                <FormGroup>
                                    <FormControlLabel control={<Checkbox value="ListerDRE" onClick={permissionsHandler}/>}
                                                      label="ListerDRE"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel control={<Checkbox value="AfficherDRE" onClick={permissionsHandler}/>}
                                                      label="AfficherDRE"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value="AfficherJustificatifs" onClick={permissionsHandler}/>}
                                        label="AfficherJustificatifs"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value=" JugerCommis" onClick={permissionsHandler}/>}
                                        label="JugerCommis"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value=" JugerDirecteur" onClick={permissionsHandler}/>}
                                        label="JugerDirecteur"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value="JugerEnseignant" onClick={permissionsHandler}/>}
                                        label="JugerEnseignant"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value="PlanifierDates" onClick={permissionsHandler}/>}
                                        label="PlanifierDates"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox value="GererUsagers" onClick={permissionsHandler}/>}
                                        label="GererUsagers"/>
                                </FormGroup>
                                <FormGroup>
                                    <FormControlLabel control={<Checkbox value="GererRoles" onClick={permissionsHandler}/>}
                                                      label="GererRoles"/>
                                </FormGroup>


                                <FormGroup>
                                    <Button color="primary" type="submit" variant="outlined">Sauvegarder</Button>{' '}
                                    <Button color="secondary" variant="outlined" onClick={routeChangeCancel}>Annuler</Button>
                                </FormGroup>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
;

export default CreerRole;
