import React, {Component} from 'react'
import {Button, Table} from "@material-ui/core";

class VoirUnRoleComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            nom: '',
            permissions: []
        }

    }

    async componentDidMount() {
        fetch(`/roles/${this.state.id}`)
            .then(response => response.json())
            .then(data => this.setState({nom: data.nom, permissions: data.permissions}));
    }

    cancel() {
        this.props.history.push('/roles');
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Roles num : {this.state.id}</h2>
                <br></br>
                <div className="row">
                    <h3>{this.state.nom}</h3>
                    <table className="table table-striped table-bordered">

                        <thead>
                        <tr>
                            <th> Permissiosn</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            {this.state.permissions.map(perm =>
                                <td>{perm}</td>
                            )}
                        </tr>
                        </tbody>
                    </table>
                    <Button color="secondary" onClick={this.cancel.bind(this)}>Retour</Button>

                </div>

            </div>
        )
    }
}

export default VoirUnRoleComponent
