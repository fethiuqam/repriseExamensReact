import '../../styles/StyleEtudiant.css'
import React, {useContext, useEffect, useState} from 'react';
import AuthContext from "../../context/AuthProvider";
import {Link, useParams} from "react-router-dom";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import SectionFormulaire from '../SectionFormulaire/SectionFormulaire';
import Box from "@mui/material/Box";
import {Button, CircularProgress, Stack} from "@mui/material";
import JugerDRE from "../JugerDRE/JugerDRE";
import KeyboardReturnIcon from "@mui/icons-material/KeyboardReturn";
import {Container} from "@mui/material";

export default function DetailsDRE() {

    const {type, permissions, jwt} = useContext(AuthContext);
    const [enChargement, setEnChargement] = useState(true);
    const [dre, setDre] = useState(null);
    const {idDRE} = useParams();
    let juge = null;

    switch (type) {
        case "enseignant" :
            juge = "enseignant";
            break;
        case "personnel" :
            if (permissions.includes("JugerCommis"))
                juge = "commis";
            else if (permissions.includes("JugerDirecteur"))
                juge = "directeur";
            break;
        default:
    }

    const API_URL = `/api/demandes/${idDRE}`;

    const checherDRE = async () => {
        try {
            const reponse = await fetch(API_URL,
                {
                    method: 'get',
                    headers: {'Authorization': 'Bearer ' + jwt}
                });
            if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
            const dre = await reponse.json();
            setDre(dre);
        } catch (err) {
            console.log(err);
        } finally {
            setEnChargement(false);
        }
    }

    useEffect(() => {
        window.scrollTo(0, 0);
        checherDRE();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [idDRE, jwt]);

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
        <MiseEnPage titre={"Détails d'une demande"}>
            {enChargement
                ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                    <CircularProgress/>
                </Box>
                : <>
                    {dre.etudiant
                        ? <SectionFormulaire title={'INFORMATIONS PERSONNELLES'}>
                            <table className="affichageInfosTable">
                                <tbody>
                                <tr>
                                    <th>Nom</th>
                                    <th>{dre.etudiant.nom}</th>
                                </tr>
                                <tr>
                                    <th>Prénom</th>
                                    <th>{dre.etudiant.prenom}</th>
                                </tr>
                                <tr>
                                    <th>Code permanent</th>
                                    <th>{dre.etudiant.codePermanent}</th>
                                </tr>
                                </tbody>
                            </table>
                        </SectionFormulaire>
                        : null
                    }
                    {dre.enseignant
                        ? <SectionFormulaire title={'INFORMATIONS ENSEIGNANT'}>
                            <table className="affichageInfosTable">
                                <tbody>
                                <tr>
                                    <th>Nom</th>
                                    <th>{dre.enseignant.nom}</th>
                                </tr>
                                <tr>
                                    <th>Prénom</th>
                                    <th>{dre.enseignant.prenom}</th>
                                </tr>
                                </tbody>
                            </table>
                        </SectionFormulaire>
                        : null
                    }

                    <SectionFormulaire title={'INFORMATIONS DEMANDE'}>
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
                    </SectionFormulaire>

                    {type === "enseignant"
                        ? null
                        : <SectionFormulaire title={'DETAILS'}>
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
                        </SectionFormulaire>


                    }


                    <Container maxWidth={false}>
                        <div style={{margin: "1.25rem"}}>
                            <Stack direction="row" spacing={1} justifyContent="space-between">

                                <Button
                                    variant="contained"
                                    component={Link}
                                    startIcon={<KeyboardReturnIcon/>}
                                    to="/"
                                    color="primary"
                                > Retour
                                </Button>

                                <Stack direction="row" spacing={1}>
                                    {juge
                                        ? <JugerDRE
                                            idDRE={idDRE}
                                            juge={juge}
                                            decisionCourante={dre.decisionCourante}
                                            actualiserDRE={checherDRE}
                                        />
                                        : null
                                    }
                                </Stack>

                            </Stack>
                        </div>
                    </Container>
                </>
            }
        </MiseEnPage>
    )
}
