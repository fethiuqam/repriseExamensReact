import React, {useEffect, useState} from "react";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";

import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Box,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from '@mui/material';
import {afficherDate} from "../../utils/utils";
import {MOTIF_AFFICHAGE} from "../../utils/const";
import Statut from "../Statut/Statut";
import Decision from "../Decision/Decision";

/* * * * * * * * Utilitaires * * * * * * * *
 *
 * Identifie les demandes qui doivent être
 * ignorées car il y a plus de trois ans
 * qu'elles ont été soumises.
 *
 * @param  JSON object : les demandes de
 *                       l'étudiant
 * @return bool[] : true  -> la demande date de
 *                           plus de trois ans
 *                  false -> autrement
 */
export const estIgnoree = (demandes) => (
    demandes.map((demande) => (
            diffJours(demande.dateHeureSoumission, new Date().getTime()) > 1095
        )
    ));

/* *
 * Retourne le nombre de jours écoulés entre
 * deux dates. Le jour de fin n'est pas inclus.
 */
export const diffJours = (debut, fin) =>
    Math.floor(Math.abs(new Date(debut) - new Date(fin)) / (60 * 60 * 24 * 1000));

/* *
 * Retourne le nombre des demandes des soumises
 * dans les trois dernières annnées.
 */
export const compterValides = (demandes) =>
    demandes.length - estIgnoree(demandes).filter(Boolean).length;

/**
 * Custom hook component crée pour communiquer
 * avec l'API.
 *
 * @param :   url de l'API
 * @return :  error state
 *            (fetched) data state
 */
export const useAPI = (url) => {

    const [hasError, setHasError] = useState(false);
    const [data, setData] = useState([]);

    useEffect(() => {
        let unmounted = false;
        const handleResponse = response => {
            if (unmounted) return [];
            setHasError(!response.ok);
            return response.ok && response.json ? response.json() : [];
        };

        const fetchData = () => {
            return fetch(url,
                {
                    method: 'get',
                    credentials: 'include'
                })
                .then(handleResponse)
                .catch(handleResponse);
        };

        if (!unmounted)
            fetchData().then(data => setData(data));

    }, [url]);

    return {hasError, data};
};

/***********************************
 * Composante exportée par default
 * qui fournit le UI.
 **********************************/
const HistoriqueEtudiant = ({idEtudiant}) => {

    const API_URL = `/api/etudiants/${idEtudiant}/historique`; /* pour visualiser le UI */
    const columns = {
        dateHeureSoumission: "Soumise le",
        motifAbsence: "Motif",
        cours: "Cours",
        statutCourant: "Statut",
        decisionCourante: "Décision"
    };

    const {data, hasError} = useAPI(API_URL, {});

    const [expanded, setExpanded] = useState(false);

    const handleChange = (panel) => (event, isExpanded) => {
        console.log("Inside handleChange");
        setExpanded(isExpanded ? panel : false);
    };

    return (
            <SectionFormulaire title={'HISTORIQUE'}>

                <Typography
                    component="span"
                    sx={{
                        fontFamily: 'Monospace',
                        fontWeight: 'bold',
                        width: '100%'
                    }}
                >
                    L'étudiant a fait {compterValides(data)} demande(s) au cours des 3 dernières années.
                </Typography>
                <br/>
                <br/>

                <Accordion
                    expanded={expanded === 'panel'}
                    onChange={handleChange('panel')}
                >
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="panel-content"
                        id="panel-header"
                    >
                        <Typography
                            component="span"
                            align="right"
                            sx={{
                                fontFamily: 'Monospace',
                                width: '100%'
                            }}
                        >
                            Voir les détails de l'historique
                        </Typography>
                    </AccordionSummary>
                    {(hasError || data.length === 0)
                        ?
                        <Box sx={{
                            display: 'flex',
                            justifyContent: 'center',
                            m: 4
                        }}
                        >
                            <h3 style={{color: 'orange'}}> Aucune donnée disponible. </h3>
                        </Box>
                        :
                        <AccordionDetails>
                            <Typography component="span">
                                <Table>
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>{columns.dateHeureSoumission}</TableCell>
                                            <TableCell>{columns.motifAbsence}</TableCell>
                                            <TableCell>{columns.cours}</TableCell>
                                            <TableCell>{columns.statutCourant}</TableCell>
                                            <TableCell>{columns.decisionCourante}</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {data.map((d) => (
                                            <TableRow key={d.id}>
                                                <TableCell>{afficherDate(d.dateHeureSoumission)}</TableCell>
                                                <TableCell>{MOTIF_AFFICHAGE[d.motifAbsence]}</TableCell>
                                                <TableCell>{d.coursGroupe.cours.sigle}</TableCell>
                                                <TableCell><Statut statut={d.statutCourant} /></TableCell>
                                                <TableCell><Decision decision={d.decisionCourante}/></TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </Typography>
                        </AccordionDetails>
                    }
                </Accordion>
                <br/>
            </SectionFormulaire>
    )
}

export default HistoriqueEtudiant;
