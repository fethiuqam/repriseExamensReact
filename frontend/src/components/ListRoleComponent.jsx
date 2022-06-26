import React, {Component} from 'react'

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


    render() {
        return (
            <div>
                <h2 className="text-center">Liste des Roles</h2>
                <div className="float-right">
                    <button className="btn btn-primary" onClick={this.ajouterRole}> Ajouter un Role</button>
                </div>
                <br></br>
                <div className="row">
                    <table className="table table-striped table-bordered">

                        <thead>
                        <tr>
                            <th> Nom</th>
                            <th> Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.roles.map(role =>
                                <tr key={role.id}>
                                    <td> {role.nom} </td>
                                    <td>
                                        <button className="btn btn-primary"
                                                onClick={() => this.modifierRole(role.id)}>MODIFIER
                                        </button>
                                        <button style={{marginLeft: "10px"}} className="btn btn-danger"
                                                onClick={() => this.remove(role.id)}>SUPPRIMER
                                        </button>
                                        <button style={{marginLeft: "10px"}} onClick={() => this.voirRole(role.id)}
                                                className="btn btn-primary">VOIR
                                        </button>
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
