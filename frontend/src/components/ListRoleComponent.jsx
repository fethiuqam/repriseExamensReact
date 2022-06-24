import React, { Component } from 'react'
import RoleService from '../services/RoleService'
import {Link} from "react-router-dom";
import {Button, ButtonGroup} from "@material-ui/core";

class ListRoleComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                roles: []
        }
        this.ajouterRole = this.ajouterRole.bind(this);
        this.modifierRole = this.modifierRole.bind(this);
        this.supprimerRole = this.supprimerRole.bind(this);
    }

    supprimerRole(id){
        RoleService.supprimerRole(id).then( res => {
            this.setState({roles: this.state.roles.filter(role => role.id !== id)});
        });
    }
    voirRole(id){
        this.props.history.push(`/view-roles/${id}`);
    }
    modifierRole(id){
        this.props.history.push(`/updates-roles/${id}`);
    }

    componentDidMount(){
        RoleService.obtenirRoles().then((res) => {
            this.setState({ roles: res.data});
        });
    }

    ajouterRole(){
        this.props.history.push('/add-roles/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Liste des Roles</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.ajouterRole}> Ajouter un Role</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Nom </th>
                                    <th> Nombre d'usagers</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.roles.map( role =>
                                        <tr key = {role.id}>
                                             <td> {role.nom} </td>
                                             <td>  </td>
                                             <td>
                                                 <button onClick={ () => this.modifierRole(role.id)} className="btn btn-primary" >MODIFIER</button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.supprimerRole(role.id)} className="btn btn-danger">SUPPRIMER </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.voirRole(role.id)} className="btn btn-primary">VOIR</button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListRoleComponent
