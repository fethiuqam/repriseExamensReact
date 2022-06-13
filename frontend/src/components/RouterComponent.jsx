import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import RoleComponent from "./RoleComponent";
import CreerRole from "./CreerRole";
import formulaire from "./CreerRole";
import React from "react";

const AppRouter = () => {
    return(
        <div style={style}>
            <Router>
                <Switch>
                    <Route path="/" exact component={RoleComponent} />
                    <Route path="/creer-role" component={CreerRole} />
                    <Route path="/form" component={formulaire} />
                </Switch>
            </Router>
        </div>
    )
}

const style={
    marginTop:'20px'
}

export default AppRouter;
