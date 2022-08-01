import React, { useState, useEffect } from 'react';
import ListeAdmin from "../ListeAdmin/ListeAdmin";
import * as apiClient from "../../api/ApiClient";


export default function ListeCours() {

    const [setCours] = useState([]);

    useEffect(() => {
        apiClient
            .get("/api/cours")
            .then((reponse) => setCours(reponse._embedded.cours));
    }, [setCours]);

    const colonnes = [
        {
            field: "sigle",
            headerName: "Sigle du cours",
            flex: 1,
            minWidth: 100,
            editable: true,
            default: "",
        },
        {
            field: "nom",
            headerName: "Nom du cours",
            flex: 1,
            minWidth: 100,
            editable: true,
            default: "",
        },
    ];

    return (
        <ListeAdmin
            titre="Cours"
            colonnes={colonnes}
            getEndpoint="cours"
        />
    );

}
