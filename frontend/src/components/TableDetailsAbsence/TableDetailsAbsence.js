import '../../styles/StyleEtudiant.css'
import {MOTIF_AFFICHAGE} from "../../utils/const";
import { Link } from '@mui/material';

export default function TableDetailsAbsence(props) {

    const {dre} = props;

    function genererItemsListe(pieces) {
        return (
            <ul>
                {pieces.map((piece) => (
                <li key={piece.id}>
                  <Link
                    component="button"
                    variant="body1"
                    onClick={() =>
                        fetch(`/api/justifications/${piece.id}/preview`, {
                            credentials: 'include'
                      })
                        .then((r) => r.blob())
                        .then(
                          (blob) =>
                            (window.open(window.URL.createObjectURL(blob), "_blank"))
                        )
                    }
                  >
                    {piece.description ?? piece.nomFichier}
                  </Link>
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
