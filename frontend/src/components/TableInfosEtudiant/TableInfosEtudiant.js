import '../../styles/StyleEtudiant.css'
import React from 'react';

export default function TableInfosEtudiant(props) {

    const {etudiant} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Nom</th>
                <th>{etudiant.nom}</th>
            </tr>
            <tr>
                <th>Prénom</th>
                <th>{etudiant.prenom}</th>
            </tr>
            <tr>
                <th>Code permanent</th>
                <th>{etudiant.codePermanent}</th>
            </tr>
            </tbody>
        </table>
    )
}