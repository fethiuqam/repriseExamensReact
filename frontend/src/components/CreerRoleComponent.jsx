import React, {Component} from 'react'
import {Button, FormGroup} from "@material-ui/core";

class CreerRoleComponent extends Component {


    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            nom: '',
            permissions: [],
        }
        this.PermissionsHandler = this.PermissionsHandler.bind(this);
        this.changeNomHandler = this.changeNomHandler.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    checkTrue() {
        document.getElementById("LISTER").checked = true;
    }


    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const role = await (await fetch(`/roles/${this.props.match.params.id}`)).json();
            this.setState({nom: role.nom, permissions: role.permissions});
        }
    }

    async handleSubmit(event) {
        event.preventDefault();
        let role = {nom: this.state.nom, permissions: this.state.permissions};
        console.log('role => ' + JSON.stringify(role));

        if (this.state.id === 'new') {
            await fetch('/roles' + (role.id ? '/' + role.id : ''), {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(role),
            });
            this.props.history.push('/roles');

        } else {
            await fetch(`/roles/${this.state.id}`, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(role),
            });
            this.props.history.push('/roles');
        }
    }

    changeNomHandler = (event) => {
        this.setState({nom: event.target.value});
    }

    PermissionsHandler(event) {
        if (event.target.checked) {
            this.setState({
                permissions: [event.target.value, ...this.state.permissions],

            }, () => {
                console.log(this.state.permissions);
            });
        }
    }


    cancel() {
        this.props.history.push('/roles');
    }

    getTitle() {
        if (this.state.id === 'new') {
            return <h3 className="text-center">Ajouter Role</h3>
        } else {
            return <h3 className="text-center">Modifier Role</h3>
        }
    }


    render() {

        return (
            <div>
                <br></br>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            {this.getTitle()}
                            <div className="card-body">
                                <form onSubmit={this.handleSubmit}>
                                    <div className="form-group">
                                        <label> Nom: </label>
                                        <input placeholder="Nom du role" name="nom" className="form-control"
                                               value={this.state.nom} onChange={this.changeNomHandler}/>
                                    </div>
                                    <div className="form-group">
                                        <label> Permissions: </label>
                                    </div>
                                    <div className="list-group">
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox" value="ListerDRE"
                                                   onClick={this.PermissionsHandler}/>
                                            ListerDRE
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox" value="AfficherDRE"
                                                   onClick={this.PermissionsHandler}/>
                                            AfficherDRE
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox"
                                                   value="AfficherJustificatifs" onClick={this.PermissionsHandler}/>
                                            AfficherJustificatifs
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox"
                                                   value="JugerRecevabilite" onClick={this.PermissionsHandler}/>
                                            JugerRecevabilite
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox"
                                                   value="PlanifierDates" onClick={this.PermissionsHandler}/>
                                            PlanifierDates
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox"
                                                   value="GererUsagers" onClick={this.PermissionsHandler}/>
                                            GererUsagers
                                        </label>
                                        <label className="list-group-item">
                                            <input className="form-check-input me-1" type="checkbox" value="GererRoles"
                                                   onClick={this.PermissionsHandler}/>
                                            GererRoles
                                        </label>
                                    </div>

                                    <FormGroup>
                                        <Button color="primary" type="submit">Sauvegarder</Button>{' '}
                                        <Button color="secondary" onClick={this.cancel.bind(this)}>Annuler</Button>
                                    </FormGroup>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default CreerRoleComponent
