//
// import React, {useState} from "react";
// //import TutorialDataService from "../services/TutorialService";
// //import axios from 'axios';
// //import FormGroup from '@mui/material/FormGroup';
// //import FormControlLabel from '@mui/material/FormControlLabel';
// //import Checkbox from '@mui/material/Checkbox';
// //import {useNavigate} from "react-router";
// import {Button, Checkbox, FormControlLabel, FormGroup} from "@mui/material";
//
//
// const CreerRoleComponent = () => {
//
//     //let navigate = useNavigate();  // TODO
//     const [id, setID] = useState(null);
//     const [nom, setNom] = useState('');
//     const [permissions, setPermissions] = useState([]);
//     //const [checkbox, setCheckbox] = useState(false);
//
//     // useEffect(() => {
//     //     setID(localStorage.getItem('ID'))
//     //     setNom(localStorage.getItem('Nom'));
//     //     setPermissions(localStorage.getItem('Permissions'));
//     //     setCheckbox(localStorage.getItem('Checkbox Value'));
//     // }, []);
//
//     const updateAPIData = async (event) => {
//
//         event.preventDefault();
//         let role = {nom, permissions};
//         console.log('role => ' + JSON.stringify(role));
//
//         await fetch('/roles' + (id ? '/' + id : ''), {
//             method: 'POST',
//             headers: {
//                 'Accept': 'application/json',
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(role),
//         });
//         //this.props.history.push('/roles'); // TODO
//     }
//
//     const updateNom = (event) => {
//         setNom(event.target.value)
//     }
//
//     const permissionsHandler = (event) => {
//         setPermissions(perm => [event.target.value, ...perm])
//     }
//
//     return (
//         <div className="container center2 mt-5">
//             <div className="row">
//
//                 <div className="card col-md-6 offset-md-3 offset-md-3 center3">
//                     <div className="card-body">
//                         <form onSubmit={updateAPIData}>
//                             <div className="form-group">
//                                 <label> Nom: </label>
//                                 <input placeholder="Nom du role" name="nom" className="form-control"
//                                        value={nom} onChange={updateNom}/>
//                             </div>
//                             <div className="form-group">
//                                 <label> Permissions: </label>
//                             </div>
//                             <FormGroup>
//                                 <FormControlLabel control={<Checkbox value="ListerDRE" onClick={permissionsHandler}/>}
//                                                   label="ListerDRE"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel control={<Checkbox value="AfficherDRE" onClick={permissionsHandler}/>}
//                                                   label="AfficherDRE"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel
//                                     control={<Checkbox value="AfficherJustificatifs" onClick={permissionsHandler}/>}
//                                     label="AfficherJustificatifs"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel
//                                     control={<Checkbox value="JugerRecevabilite" onClick={permissionsHandler}/>}
//                                     label="JugerRecevabilite"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel
//                                     control={<Checkbox value="PlanifierDates" onClick={permissionsHandler}/>}
//                                     label="PlanifierDates"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel
//                                     control={<Checkbox value="GererUsagers" onClick={permissionsHandler}/>}
//                                     label="GererUsagers"/>
//                             </FormGroup>
//                             <FormGroup>
//                                 <FormControlLabel control={<Checkbox value="GererRoles" onClick={permissionsHandler}/>}
//                                                   label="GererRoles"/>
//                             </FormGroup>
//
//
//                             <FormGroup>
//                                 <Button color="primary" type="submit">Sauvegarder</Button>{' '}
//                                 <Button color="secondary">Annuler</Button>  // TODO
//                             </FormGroup>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//
//     );
// };
//
// export default CreerRoleComponent;
