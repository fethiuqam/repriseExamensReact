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
import Formulaire from "./components/Formulaire/Formulaire";
import ListRole from "./components/Role/ListerRole";
import VoirUnRole from "./components/Role/VoirUnRole";
import CreerModifierRole from "./components/Role/CreerModifierRole";

function App() {

    return (
        <BrowserRouter>
            <Grid style={{minHeight: "100vh"}} container direction='column' alignItems="stretch"
                  justifyContent="space-between">
                <Entete/>
                <Routes>

                    <Route exact path="/connexion" element={<Connexion/>} />

                    <Route element={<AuthRequise typesPermis={['directeur', 'personnel', 'enseignant', 'etudiant']} />}>
                        <Route exact path="/" element={<ListeDRE/>} />
                    </Route>

                    <Route element={<AuthRequise typesPermis={['etudiant']} />}>
                        <Route exact path="/faire-demande" element={<Formulaire/>} />
                    </Route>


                    <Route exact path="/roles" element={<ListRole/>} />
                    <Route exact path="/roles/:id" element={<CreerModifierRole/>} />
                    <Route exact path="/voir-roles/:id" element={<VoirUnRole/>}/>


                    <Route path="/non-autorise" element={<NonAutorise/>} />

                </Routes>
                <PiedPage/>
            </Grid>
        </BrowserRouter>
    );
}

export default App;




