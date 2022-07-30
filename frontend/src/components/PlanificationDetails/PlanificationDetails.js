import '../../styles/StyleEtudiant.css'
import React from "react";
import {afficherDateHeure} from "../../utils/utils";

export default function PlanificationDetails ({reprise}) {

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Date et horaire de la reprise</th>
                <td>{afficherDateHeure(reprise.dateHeure)}</td>
            </tr>
            <tr>
                <th>Durée de l'épreuve</th>
                <td>{reprise.dureeMinutes} min</td>
            </tr>
            <tr>
                <th>Local no </th>
                <td>{reprise.local}</td>
            </tr>
            <tr>
                <th>Nom du surveillant</th>
                <td>{reprise.surveillant}</td>
            </tr>
            </tbody>
        </table>
    )
}
