// import React, {useState, useEffect, useCallback, useContext} from 'react';
// import AuthContext from "../../context/AuthProvider";
// import TableContainer from "@mui/material/TableContainer";
// import Paper from "@mui/material/Paper";
// import Button from "@mui/material/Button";
// import Table from "@mui/material/Table";
// import TableHead from "@mui/material/TableHead";
// import TableRow from "@mui/material/TableRow";
// import TableCell from "@mui/material/TableCell";
// import TableBody from "@mui/material/TableBody";
//
//
// export default function ListerRole() {
//
//     const {id, jwt} = useContext(AuthContext);
//
//     const [listeRole, setListeRole] = useState({data: []});
//     //const [listeDREFiltree, setListeDREFiltree] = useState([]);
//     const [fetchError, setFetchError] = useState(null);
//     const [enChargement, setEnChargement] = useState(true);
//     // const [colonnes, setColonnes] = useState([
//     //     ...[{nom: 'Nom'}]
//     // ]);
//
//     useEffect(() => {
//         const API_URL = `/api/roles?id=${id ? id : ""}`;
//         const fetchItems = async () => {
//             try {
//                 const reponse = await fetch(API_URL,
//                     {
//                         method: 'get',
//                         headers: {'Authorization':'Bearer ' + jwt}
//                     });
//                 if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
//                 const listerole = await reponse.json();
//                 setListeRole(listerole);
//                 setFetchError(null);
//             } catch (err) {
//                 setFetchError(err.message);
//             } finally {
//                 setEnChargement(false);
//             }
//         }
//         fetchItems()
//             .then(r =>r.json())
//             .then(data => setListeRole(data.message));
//     }, [id, jwt]);
//
//
//     return (
//         <TableContainer component={Paper}>
//             <Button variant="outlined" color="primary" > Ajouter un Role</Button>
//             <Table sx={{minWidth: 650}} aria-label="simple table">
//                 <TableHead>
//                     <TableRow>
//                         <TableCell>Nom </TableCell>
//                         <TableCell align="right">Actions</TableCell>
//                     </TableRow>
//                 </TableHead>
//                 <TableBody>
//                     {listeRole
//                         .map(role =>
//                         <TableRow
//                             key={role.id}
//                             sx={{'&:last-child td, &:last-child th': {border: 0}}}
//                         >
//                             <TableCell component="th" scope="row">
//                                 {role.nom}
//                             </TableCell>
//                             <TableCell align="right">
//                                 <Button variant="contained" color="primary"
//                                         >
//                                     MODIFIER
//                                 </Button>
//                                 <Button variant="contained" color="error" >
//                                     SUPPRIMER
//                                 </Button>
//                                 <Button variant="outlined" >
//                                     VOIR
//                                     {/*<Link to="voir/1">VOIR</Link>*/}
//                                 </Button>
//                             </TableCell>
//                         </TableRow>
//                     )}
//                 </TableBody>
//             </Table>
//         </TableContainer>
//     );
// }
