import '../../styles/StyleEtudiant.css'
import React from 'react';
import {afficherDate} from "../../utils/utils";
import {DECISION_AFFICHAGE, STATUT_AFFICHAGE} from "../../utils/const";

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
                    <td>{dre.statutCourant != null ? STATUT_AFFICHAGE[dre.statutCourant] : ""}</td>
                </tr>
                <tr>
                    <th>Décision</th>
                    <td>{dre.decisionCourante != null ? DECISION_AFFICHAGE[dre.decisionCourante] : "Aucune"}</td>
                </tr>
                <tr>
                    <th>Type d'évaluation</th>
                    <td>{dre.examen != null ? dre.examen : ""}</td>
                </tr>
            </tbody>
        </table>
    )
}