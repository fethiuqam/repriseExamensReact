import '../../styles/StyleEtudiant.css'
import React from 'react';

export default function TableInfosEtudiant(props) {

    const {etudiant} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Nom</th>
                <td>{etudiant.nom}</td>
            </tr>
            <tr>
                <th>Prénom</th>
                <td>{etudiant.prenom}</td>
            </tr>
            <tr>
                <th>Code permanent</th>
                <td>{etudiant.codePermanent}</td>
            </tr>
            </tbody>
        </table>
    )
}