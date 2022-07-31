import React from "react";
import Grid from '@mui/material/Grid';
import "./App.css";
import {BrowserRouter, Route} from 'react-router-dom';
import ListeDRE from "./components/ListeDRE/ListeDRE";
import Entete from "./components/Entete/Entete";
import PiedPage from "./components/PiedPage/PiedPage";
import Connexion from "./components/Connexion/Connexion";
import {Routes} from "react-router";
import AuthRequise from "./components/AuthRequise/AuthRequise";
import NonAutorise from "./components/NonAutorise/NonAutorise";
import NonTrouve from "./components/NonTrouve/NonTrouve";
import Formulaire from "./components/Formulaire/Formulaire";
import {Permission, TypeId} from "./utils/const";
import ListeUtilisateurs from "./components/ListeUtilisateurs/ListeUtilisateurs"
import DetailsDRE from "./components/DetailsDRE/DetailsDRE";
import ListeRoles from "./components/ListeRoles/ListeRoles"
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import Planification from "./components/Planification/Planification";

function App() {

    return (
        <BrowserRouter>
            <Grid style={{minHeight: "100vh"}} container direction='column' alignItems="stretch"
                  justifyContent="space-between">
                <Entete/>
                <Routes>

                    <Route exact path="/connexion" element={<Connexion/>}/>

                    <Route element={<AuthRequise typesPermis={['personnel', 'enseignant', 'etudiant']}/>}>
                        <Route exact path="/" element={<ListeDRE/>}/>
                    </Route>

                    <Route element={<AuthRequise typesPermis={['etudiant']}/>}>
                        <Route exact path="/faire-demande" element={<Formulaire/>}/>
                    </Route>

                    <Route element={<AuthRequise typesPermis={['personnel']}/>}>
                        <Route exact path="/planification" element={<Planification/>}/>
                    </Route>

                    <Route element={<AuthRequise typesPermis={['personnel', 'enseignant', 'etudiant']}/>}>
                        <Route exact path="/details/:idDRE" element={<DetailsDRE/>}/>
                    </Route>

                    <Route path="/non-autorise" element={<NonAutorise/>}/>

                    <Route path="*" element={<NonTrouve/>}/>

                    <Route element={<AuthRequise typesPermis={[TypeId.Personnel]}
                                                 permissionsRequises={[Permission.GererUsagers]}/>}>
                        <Route exact path="/utilisateurs" element={<ListeUtilisateurs/>}/>
                    </Route>

                    <Route element={<AuthRequise typesPermis={[TypeId.Personnel]}
                                                 permissionsRequises={[Permission.GererRoles]}/>}>
                        <Route exact path="/roles" element={<ListeRoles/>}/>
                    </Route>

                </Routes>
                <PiedPage/>
            </Grid>
        </BrowserRouter>
    );
}

export default App;
