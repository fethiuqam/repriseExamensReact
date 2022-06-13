import React from 'react';
import RoleService from '../services/RoleService';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@material-ui/core";
import Button from "@material-ui/core/Button";

class RoleComponent extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            roles: [],
            message: null
        }
        this.addRole= this.addRole.bind(this);
    }

    componentDidMount() {
        RoleService.getRoles()
            .then((res) => {
                this.setState({roles: res.data.result})
            });
    }

    addRole() {
        this.props.history.push('/form');
    }

    render (){
        return (
            <TableContainer component={Paper}>
                <h1 className = "text-center"> Roles List</h1>
                <Button variant="contained" align="right" onClick={() => this.addRole()}>Creer un role</Button>

                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Nom</TableCell>
                            <TableCell>Nombre d'usagers</TableCell>
                        </TableRow>
                    </TableHead>
                        <TableBody>
                                <TableRow>
                                    <TableCell>Directeur du Département</TableCell>
                                    <TableCell align="center">1</TableCell>
                                </TableRow>
                        </TableBody>
                </Table>
            </TableContainer>
        )
    }
    // render (){
    //     return (
    //     <TableContainer component={Paper}>
    //         <h1 className = "text-center"> Roles List</h1>
    //         <Button variant="contained" align="right" onClick={() => this.addRole()}>Creer un role</Button>
    //
    //         <Table sx={{ minWidth: 650 }} aria-label="simple table">
    //             <TableHead>
    //                 <TableRow>
    //                     <TableCell>Nom</TableCell>
    //                     <TableCell>Nombre d'usagers</TableCell>
    //                 </TableRow>
    //             </TableHead>
    //             {this.state.roles.length > 0 && (
    //             <TableBody>
    //                 {this.state.roles.map(role => (
    //                     <TableRow
    //                         key={role.id}
    //                         sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
    //                     >
    //                         <TableCell component="th" scope="row">
    //                             {role.nom}
    //                         </TableCell>
    //                     </TableRow>
    //                 ))}
    //             </TableBody>
    //             )}
    //         </Table>
    //
    //     </TableContainer>
    //     )
    // }
}

export default RoleComponent;

