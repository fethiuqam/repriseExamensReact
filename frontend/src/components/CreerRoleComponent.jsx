import React, { Component } from 'react'
import RoleService from '../services/RoleService';

class CreerRoleComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            firstName: '',
            permissions: []
        }
        this.PermissionsHandler = this.PermissionsHandler.bind(this);
        this.changeNomHandler = this.changeNomHandler.bind(this);
    }

    componentDidMount(){
        if(this.state.id === '_add'){
            return
        }else{
            RoleService.getRoleById(this.state.id).then( (res) =>{
                let role = res.data;
                this.setState({nom: role.nom,
                    permissions: role.permissions
                });
            });
        }
    }
    sauvegarderOuModifierRole = (e) => {
        e.preventDefault();
        let role = {nom: this.state.nom, permissions: this.state.permissions};
        console.log('role => ' + JSON.stringify(role));

        // step 5
        if(this.state.id === '_add'){
            RoleService.creerRole(role).then(res =>{
                this.props.history.push('/roles');
            });
        }else{
            RoleService.modifierRole(role, this.state.id).then( res => {
                this.props.history.push('/roles');
            });
        }
    }

    changeNomHandler= (event) => {
        this.setState({nom: event.target.value});
    }

    PermissionsHandler(e) {
        if(e.target.checked) {
            this.setState({
                permissions: [ e.target.value, ...this.state.permissions],
            }, () => {
                console.log(this.state.permissions);
            });
        }
    }

    cancel(){
        this.props.history.push('/roles');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Ajouter Role</h3>
        }else{
            return <h3 className="text-center">Creer Role</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Nom: </label>
                                            <input placeholder="Nom du role" name="nom" className="form-control"
                                                value={this.state.nom} onChange={this.changeNomHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Permissions: </label>
                                            {/*<input placeholder="permissions" name="permissions" className="form-control"*/}
                                            {/*    value={this.state.permissions}/>*/}
                                        </div>
                                        <div className="list-group">
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="ListerDRE" onClick={this.PermissionsHandler}/>
                                                ListerDRE
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="AfficherDRE" onClick={this.PermissionsHandler}/>
                                                AfficherDRE
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="AfficherJustificatifs" onClick={this.PermissionsHandler}/>
                                                AfficherJustificatifs
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="JugerRecevabilite" onClick={this.PermissionsHandler}/>
                                                JugerRecevabilite
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="PlanifierDates" onClick={this.PermissionsHandler}/>
                                                PlanifierDates
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="GererUsagers" onClick={this.PermissionsHandler}/>
                                                GererUsagers
                                            </label>
                                            <label className="list-group-item">
                                                <input className="form-check-input me-1" type="checkbox" value="GererRoles" onClick={this.PermissionsHandler}/>
                                                GererRoles
                                            </label>
                                        </div>

                                        <button className="btn btn-success" onClick={this.sauvegarderOuModifierRole}>Creer</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Annuler</button>
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
