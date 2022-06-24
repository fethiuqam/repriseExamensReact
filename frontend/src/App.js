import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListRoleComponent from './components/ListRoleComponent';
import EnTeteComponent from './components/EnTeteComponent';
import PiedDePageComponent from './components/PiedDePageComponent';
import CreerRoleComponent from './components/CreerRoleComponent';
import ModifierRoleComponent from './components/ModifierRoleComponent';
//import VoirUnRoleComponent from './components/VoirUnRoleComponent';

function App() {
    return (
        <div>
            <Router>
                <EnTeteComponent />
                <div className="container">
                    <Switch>
                        <Route path = "/" exact component = {ListRoleComponent}></Route>
                        <Route path = "/roles" component = {ListRoleComponent}></Route>
                        <Route path = "/add-roles/:id" component = {CreerRoleComponent}></Route>
                        {/*<Route path = "/view-roles/:id" component = {VoirUnRoleComponent}></Route>*/}
                        <Route path = "/update-roles/:id" component = {ModifierRoleComponent}></Route>
                    </Switch>
                </div>
                <PiedDePageComponent />
            </Router>
        </div>

    );
}

export default App;
