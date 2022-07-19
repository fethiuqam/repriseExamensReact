import React, {useEffect, useState} from 'react'
import { useParams} from "react-router-dom";
import {FormGroup} from "@mui/material";


const VoirUnRole = props => {
    const {id} = useParams();
    //let navigate = useNavigate();

    const [nom, setNom] = useState('');
    const [permissions, setPermissions] = useState([]);
    const [fetchError, setFetchError] = useState(null);

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const reponse = await fetch(`/roles/${id}`,
                    {
                        method: 'get'
                    });
                const rolesList = await reponse.json();
                setNom(rolesList.nom);
                setPermissions(rolesList.permissions);
            } catch (err) {
                setFetchError(err.message);
            }
        }
        fetchItems();
    },[]);


    return (
        <div className="container center2 mt-5">

            <div className="row">

                <div className="card col-md-6 offset-md-3 offset-md-3 center3">
                    <div className="card-body">
                        <form>
                            <div className="form-group">
                                <h2> {nom} </h2>
                            </div>
                            <div className="form-group">
                                <label> Permissions: </label>
                            </div>
                            {permissions.map(perm =>
                                <FormGroup>
                                    {perm}
                                </FormGroup>
                            )}
                        </form>
                    </div>
                </div>
            </div>
        </div>

    );
}
export default VoirUnRole
