import '../../styles/StyleEtudiant.css'
import React from 'react';
import {MOTIF_AFFICHAGE} from "../../utils/const";

export default function TableDetailsAbsence(props) {

    const {dre} = props;

    function genererItemsListe(pieces) {
        return (
            <ul>
                {pieces.map((piece) => (
                    <li key={piece.url}>
                        <a href={`\\${piece.url}`}>
                            {piece.description}
                        </a>
                    </li>
                ))}
            </ul>
        )
    }

    return (
        <div>
            <table className="affichageInfosTable">
                <tbody>
                <tr>
                    <th>Motif</th>
                    <td>{dre.motifAbsence != null ? MOTIF_AFFICHAGE[dre.motifAbsence] : ""}</td>
                </tr>
                {dre.absenceDetails
                    ? <tr>
                        <th>Détails motif</th>
                        <td> {dre.absenceDetails}</td>
                    </tr>
                    : null
                }
                </tbody>
            </table>
            {dre.justifications
                ? <div style={{padding: '1rem'}}>
                    <h2>Pièces justificatives</h2>
                    <ul>
                        {genererItemsListe(dre.justifications)}
                    </ul>
                </div>
                : null
            }    
        </div>
    )
}