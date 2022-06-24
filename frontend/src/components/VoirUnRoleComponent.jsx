import React, {Component} from 'react'
import RoleService from '../services/RoleService'
import {Table} from "@material-ui/core";

class VoirUnRoleComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            roles: {}
        }
    }

    componentDidMount() {
        RoleService.obtenirRoleParId(this.state.id).then(res => {
            this.setState({roles: res.data});
        })
    }

    render() {

        const clientList = this.state.permissions.map(permission => {
            return <tr>
                <td style={{whiteSpace: 'nowrap'}}>{permission}</td>
            </tr>
        });

        return (
            <div>
                <br></br>
                <div className="card col-md-6 offset-md-3">
                    <h3 className="text-center"> Role Details </h3>
                    <div className="card-body">
                        <div className="row">
                            <label> Nom: </label>
                            <div> {this.state.roles.nom}</div>
                        </div>
                        <Table className="mt-4">
                            <thead>
                            Permissions
                            </thead>
                            <tbody>
                            {clientList}
                            </tbody>
                        </Table>
                    </div>
                </div>
            </div>
        )
    }
}

export default VoirUnRoleComponent
