import '../../styles/StyleEtudiant.css'
import React from 'react';
import {afficherDate} from "../../utils/utils";
import Statut from "../Statut/Statut";
import Decision from "../Decision/Decision";

export default function TableInfosDemande(props) {

    const {dre} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Date de début d'absence</th>
                <td>{dre.absenceDateDebut != null ? afficherDate(dre.absenceDateDebut) : ""}</td>
            </tr>
            <tr>
                <th>Date de fin d'absence</th>
                <td>{dre.absenceDateFin != null ? afficherDate(dre.absenceDateFin) : ""}</td>
            </tr>
            <tr>
                <th>Date de soumission</th>
                <td>{dre.dateHeureSoumission != null ? afficherDate(dre.dateHeureSoumission) : "Non soumise"}</td>
            </tr>
            <tr>
                <th>Status</th>
                <td>{dre.statutCourant ? <Statut statut={dre.statutCourant}/> : ""}</td>
            </tr>
            <tr>
                <th>Décision</th>
                <td><Decision decision={dre.decisionCourante}/></td>
            </tr>
            <tr>
                <th>Type d'évaluation</th>
                <td>{dre.examen != null ? dre.examen : ""}</td>
            </tr>
            </tbody>
        </table>
    )
}