import React from 'react';
import './App.css';
import NavBar from './components/Navbar';
import AppRouter from "./components/RouterComponent";
import {Container} from "@material-ui/core";

function App() {
    return (
        <div className="App">
            <NavBar/>
            <Container>
                <AppRouter/>
            </Container>
        </div>
    );
}

export default App;

