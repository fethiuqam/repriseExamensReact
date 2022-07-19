import '../../styles/StyleEtudiant.css'
import React from 'react';

export default function TableInfosEnseignant(props) {

    const {enseignant} = props;

    return (
        <table className="affichageInfosTable">
            <tbody>
            <tr>
                <th>Nom</th>
                <td>{enseignant.nom}</td>
            </tr>
            <tr>
                <th>Prénom</th>
                <td>{enseignant.prenom}</td>
            </tr>
            <tr>
                <th>Matricule</th>
                <td>{enseignant.matricule}</td>
            </tr>
            </tbody>
        </table>
    )
}