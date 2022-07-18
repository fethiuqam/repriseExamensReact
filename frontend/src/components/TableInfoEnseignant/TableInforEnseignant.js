import '../../styles/StyleEtudiant.css'
import React from 'react';

export default function TableInfosEnseignant(props) {

    const {enseignant} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Nom</th>
                <th>{enseignant.nom}</th>
            </tr>
            <tr>
                <th>Prénom</th>
                <th>{enseignant.prenom}</th>
            </tr>
            <tr>
                <th>Code permanent</th>
                <th>{enseignant.codePermanent}</th>
            </tr>
            </tbody>
        </table>
    )
}