import React, {useContext, useState} from "react";
//import TutorialDataService from "../services/TutorialService";
//import axios from 'axios';
//import FormGroup from '@mui/material/FormGroup';
//import FormControlLabel from '@mui/material/FormControlLabel';
//import Checkbox from '@mui/material/Checkbox';
import {useNavigate} from "react-router";
import {Button, Checkbox, FormControlLabel, FormGroup} from "@mui/material";
import AuthContext from "../../context/AuthProvider";


const CreerRole = () => {

        let navigate = useNavigate();
        const {jwt} = useContext(AuthContext);
        const [nom, setNom] = useState('');
        const [permissions, setPermissions] = useState([]);
        const [fetchError, setFetchError] = useState(null);
        //const [checkbox, setCheckbox] = useState(false);


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
                                        control={<Checkbox value="JugerRecevabilite" onClick={permissionsHandler}/>}
                                        label="JugerRecevabilite"/>
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
                                    <Button color="primary" type="submit">Sauvegarder</Button>{' '}
                                    <Button color="secondary">Annuler</Button>
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
