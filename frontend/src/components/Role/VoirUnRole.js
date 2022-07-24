import React, {useContext, useEffect, useState} from 'react'
import {useNavigate, useParams} from "react-router-dom";
import {Button, FormGroup} from "@mui/material";
import Box from "@mui/material/Box";
import AuthContext from "../../context/AuthProvider";

const VoirUnRole = props => {

    const {id} = useParams();
    let navigate = useNavigate();
    const {jwt} = useContext(AuthContext);
    const [nom, setNom] = useState('');
    const [permissions, setPermissions] = useState([]);
    const [fetchError, setFetchError] = useState(null);

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const reponse = await fetch(`/roles/${id}`,
                    {
                        method: 'get',
                        headers: { 'Authorization': 'Bearer ' + jwt }
                    });
                const rolesList = await reponse.json();
                setNom(rolesList.nom);
                setPermissions(rolesList.permissions);
            } catch (err) {
                setFetchError(err.message);
            }
        }
        fetchItems();
    }, [id, jwt]);


    const routeChangeCancel = () => {
        let path = `/roles`;
        navigate(path);
    }

    return (
        <div className="container center2 mt-5">

            <div className="row">

                <div className="card col-md-6 offset-md-3 offset-md-3 center3">
                    <div className="card-body">
                        <form>
                            <div className="form-group">
                                <h2> {nom} </h2>
                            </div>
                            {fetchError && <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                <h3 style={{color: 'red'}}>{fetchError}</h3>
                            </Box>}
                            <div className="form-group">
                                <label> Permissions: </label>
                            </div>
                            {permissions.map(perm =>
                                <FormGroup>
                                    {perm}
                                </FormGroup>
                            )}

                            <FormGroup>
                                <Button color="secondary" variant="outlined" onClick={routeChangeCancel}>Retour</Button>
                            </FormGroup>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    );
}
export default VoirUnRole
