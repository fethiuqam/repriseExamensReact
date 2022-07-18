import '../../styles/StyleEtudiant.css'
import React from 'react';

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
                    <th>{dre.motifAbsence != null ? dre.motifAbsence : ""}</th>
                </tr>
                {dre.absenceDetails
                    ? <tr>
                        <th>Détails motif</th>
                        <th> {dre.absenceDetails}</th>
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