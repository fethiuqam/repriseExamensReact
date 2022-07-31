import '../../styles/StyleEtudiant.css'
import React from 'react';
import {afficherDate} from "../../utils/utils";
import Statut from "../Statut/Statut";
import Decision from "../Decision/Decision";
import Grid from "@mui/material/Grid";

export default function TableInfosDemande(props) {

    const {dre} = props;

    return (
        <Grid container direction="row" >

            <Grid item xs={12} md={6}>
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
                        <th>Type d'évaluation</th>
                        <td>{dre.examen != null ? dre.examen : ""}</td>
                    </tr>
                    </tbody>
                </table>
            </Grid>

            <Grid item xs={12} md={6}>
                <table className="affichageInfosTable">
                    <tbody>
                    <tr>
                        <th>Status</th>
                        <td>{dre.statutCourant ? <Statut statut={dre.statutCourant}/> : ""}</td>
                    </tr>
                    {dre.detailsRejet
                        ? <tr>
                            <th>Détails du rejet</th>
                            <td>{dre.detailsRejet}</td>
                        </tr>
                        : null
                    }
                    {dre.detailsRetour
                        ? <tr>
                            <th>Détails du retour</th>
                            <td>{dre.detailsRetour}</td>
                        </tr>
                        : null
                    }
                    {dre.decisionCourante
                        ?
                        <tr>
                            <th>Décision</th>
                            <td><Decision decision={dre.decisionCourante}/></td>
                        </tr>
                        : null
                    }
                    {dre.decisionCourante?.details
                        ?
                        <tr>
                            <th>Détails de la décision</th>
                            <td>{dre.decisionCourante.details}</td>
                        </tr>
                        : null
                    }
                    </tbody>
                </table>
            </Grid>

        </Grid>

    )
}