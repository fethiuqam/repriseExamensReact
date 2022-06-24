import React, { Component } from 'react'
import RoleService from '../services/RoleService';

class ModifierRoleComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            nom: '',
            permissions: []
        }
        this.changeNomHandler = this.changeNomHandler.bind(this);
        this.PermissionsHandler = this.PermissionsHandler.bind(this);
        this.modifierRole = this.modifierRole.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentDidMount(){
        RoleService.obtenirRoleParId(this.state.id).then( (res) =>{
            let role = res.data;
            this.setState({nom: role.nom,
                permissions: role.permissions
            });
        });
    }

    modifierRole = (e) => {
        e.preventDefault();
        let role = {nom: this.state.nom, permissions: this.state.permissions};
        console.log('role => ' + JSON.stringify(role));
        console.log('id => ' + JSON.stringify(this.state.id));
        RoleService.modifierRole(role, this.state.id).then( res => {
            this.props.history.push('/roles');
        });
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

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch('/api/roles' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/roles');
    }

    cancel(){
        this.props.history.push('/roles');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Modifier Role</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> First Name: </label>
                                            <input placeholder="First Name" name="nom" className="form-control"
                                                value={this.state.nom} onChange={this.changeNomHandler}/>
                                        </div>

                                        <button className="btn btn-success" onClick={this.handleSubmit}>Modifier</button>
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

export default ModifierRoleComponent
