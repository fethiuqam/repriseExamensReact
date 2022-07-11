import React from "react";
import Grid from '@mui/material/Grid';
import "./App.css";
import {BrowserRouter, Route} from 'react-router-dom';
import ListeDRE from "./components/listeDRE/ListeDRE";
import Entete from "./components/Entete/Entete";
import PiedPage from "./components/PiedPage/PiedPage";
import Connexion from "./components/Connexion/Connexion";
import {Routes} from "react-router";
import AuthRequise from "./components/AuthRequise/AuthRequise";
import NonAutorise from "./components/NonAutorise/NonAutorise";
import NonTrouve from "./components/NonTrouve/NonTrouve";
import Formulaire from "./components/Formulaire/Formulaire";

function App() {

    return (
        <BrowserRouter>
            <Grid style={{minHeight: "100vh"}} container direction='column' alignItems="stretch"
                  justifyContent="space-between">
                <Entete/>
                <Routes>

                    <Route exact path="/connexion" element={<Connexion/>} />

                    <Route element={<AuthRequise rolesPermis={['directeur', 'commis', 'enseignant', 'etudiant']} />}>
                        <Route exact path="/" element={<ListeDRE/>} />
                    </Route>

                    <Route element={<AuthRequise rolesPermis={['etudiant']} />}>
                        <Route exact path="/faire-demande" element={<Formulaire/>} />
                    </Route>

                    <Route path="/non-autorise" element={<NonAutorise/>} />

                    <Route path="*" element={<NonTrouve/>} />


                </Routes>
                <PiedPage/>
            </Grid>
        </BrowserRouter>
    );
}

export default App;
