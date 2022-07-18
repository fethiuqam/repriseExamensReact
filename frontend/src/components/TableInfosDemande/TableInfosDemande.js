import '../../styles/StyleEtudiant.css'
import React from 'react';

export default function TableInfosDemande(props) {

    const {dre} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
                <tr>
                    <th>Date de début d'absence</th>
                    <th>{dre.absenceDateDebut != null ? dre.absenceDateDebut : ""}</th>
                </tr>
                <tr>
                    <th>Date de fin d'absence</th>
                    <th>{dre.absenceDateFin != null ? dre.absenceDateFin : ""}</th>
                </tr>
                <tr>
                    <th>Date de soumission</th>
                    <th>{dre.dateHeureSoumission != null ? dre.dateHeureSoumission.slice(0, 10) : "N/A"}</th>
                </tr>
                <tr>
                    <th>Status</th>
                    <th>{dre.statutCourant != null ? dre.statutCourant : ""}</th>
                </tr>
                <tr>
                    <th>Décision</th>
                    <th>{dre.decisionCourante != null ? dre.decisionCourante : "AUCUNE"}</th>
                </tr>
                <tr>
                    <th>Type d'évaluation</th>
                    <th>{dre.examen != null ? dre.examen : ""}</th>
                </tr>
            </tbody>
        </table>
    )
}