import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListRoleComponent from './components/ListRoleComponent';
import EnTeteComponent from './components/EnTeteComponent';
import PiedDePageComponent from './components/PiedDePageComponent';
import CreerRoleComponent from './components/CreerRoleComponent';
import VoirUnRoleComponent from './components/VoirUnRoleComponent';

function App() {
    return (
        <div>
            <Router>
                <EnTeteComponent />
                <div className="container">
                    <Switch>
                        <Route path = "/" exact component = {ListRoleComponent}></Route>
                        <Route path = "/roles" exact component = {ListRoleComponent}></Route>
                        <Route path = "/roles/:id" component = {CreerRoleComponent}></Route>
                        <Route path = "/voir-roles/:id" component = {VoirUnRoleComponent}></Route>
                    </Switch>
                </div>
                <PiedDePageComponent />
            </Router>
        </div>

    );
}

export default App;
