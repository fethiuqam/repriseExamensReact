import React, {Component} from 'react';
import {confirmAlert} from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css'; // Import css
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
//import LigneDRE from "./ligneDRE/LigneDRE";
import Button from "@mui/material/Button";
//import {useNavigate} from "react-router-dom";
//import {Link} from 'react-router-dom';
//import {useParams} from "react-router";
//import { createBrowserHistory } from "history";


class ListRoleComponent extends Component {


    constructor(props) {
        super(props)

        this.state = {
            roles: []
        }
        this.remove = this.remove.bind(this);
        this.ajouterRole = this.ajouterRole.bind(this);
    }


    componentDidMount() {
        fetch('/roles')
            .then(response => response.json())
            .then(data => this.setState({roles: data}));
    }

    navigateToHome(id) {
        const { history } = this.props;
        history.push(`/voir-roles/${id}`);
    }

    async remove(id) {
        await fetch(`/roles/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedRoles = [...this.state.roles].filter(i => i.id !== id);
            this.setState({roles: updatedRoles});
        });
    }


    ajouterRole() {
        this.props.history.push('/roles/new');
    }

    modifierRole(id) {
        this.props.history.push(`/roles/${id}`);
    }


    voirRole(id) {
        this.props.history.push(`/voir-roles/${id}`);
    }


    cancel() {
        this.props.history.push('/roles');
    }

    submit = (id) => {
        confirmAlert({
            title: 'Confirmation de suppression',
            message: 'Êtes-vous sur de vouloir supprimer ce rôle ?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.remove(id)
                },
                {
                    label: 'No',
                    onClick: () => this.cancel()
                }
            ]
        });
    };


    render() {
        const {history } = this.props;
        return (
            <TableContainer component={Paper}>
                <Button variant="outlined" color="primary" onClick={this.ajouterRole}> Ajouter un Role</Button>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Nom </TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.roles.map(role =>
                            <TableRow
                                key={role.id}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {role.nom}
                                </TableCell>
                                <TableCell align="right">
                                    <Button variant="contained" color="primary"
                                            onClick={() => this.modifierRole(role.id)}>
                                        MODIFIER
                                    </Button>
                                    <Button variant="contained" color="error" onClick={() => this.submit(role.id)}>
                                        SUPPRIMER
                                    </Button>
                                    <Button variant="outlined" onClick={() => this.voirRole(role.id)} >
                                        VOIR
                                        {/*<Link to="voir/1">VOIR</Link>*/}
                                    </Button>
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default ListRoleComponent
