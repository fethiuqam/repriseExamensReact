import React, {useState, useEffect, useCallback, useContext} from 'react';
import FiltreListeDRE from "../FiltreListeDRE/FiltreListeDRE";
import TableListeDRE from "../tableListeDRE/TableListeDRE";
import AuthContext from "../../context/AuthProvider";
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import Box from "@mui/material/Box";
import {CircularProgress} from "@mui/material";

const STATUTS = [
    'ENREGISTREE',
    'SOUMISE',
    'EN_TRAITEMENT',
    'ACCEPTEE',
    'VALIDEE',
    'REJETEE',
    'ANNULEE',
    'COMPLETEE'
];

const comparator = (prop, desc = true) => (a, b) => {
    const order = desc ? -1 : 1;
    if (a[prop] < b[prop]) {
        return -1 * order;
    }
    if (a[prop] > b[prop]) {
        return 1 * order;
    }
    return 0;
};

export default function ListeDRE() {

    const {role, id} = useContext(AuthContext);

    const [listeDRE, setListeDRE] = useState([]);
    const [listeDREFiltree, setListeDREFiltree] = useState([]);
    const [fetchError, setFetchError] = useState(null);
    const [enChargement, setEnChargement] = useState(true);
    const [colonnes, setColonnes] = useState([
        ...[{name: 'Soumise le', prop: 'dateHeureSoumission', active: false}],
        ...(role === "etudiant" ? [] : [{name: 'Étudiant', prop: 'nomEtudiant', active: false}]),
        ...(role === "enseignant" ? [] : [{name: 'Enseignant', prop: 'nomEnseignant', active: false}]),
        ...[{name: 'Cours', prop: 'sigleCours', active: false},
            {name: 'Session', prop: 'session', active: false},
            {name: 'Statut', prop: 'statutCourant', active: false}]
    ]);

    useEffect(() => {
        const API_URL = `/api/demandes?role=${role}&id=${id ? id : ""}`;
        const fetchItems = async () => {
            try {
                const reponse = await fetch(API_URL);
                if (!reponse.ok) throw Error('Un problème est survenu lors du chargement des données.');
                const listedre = await reponse.json();
                setListeDRE(listedre);
                setListeDREFiltree(listedre);
                setFetchError(null);
            } catch (err) {
                setFetchError(err.message);
            } finally {
                setEnChargement(false);
            }
        }
        fetchItems();
    }, [role, id])


    const filtrer = useCallback((filtre) => {
        let filtreItems = [...listeDRE].filter(item =>
            filtre.statuts.includes(item.statutCourant)
            && item.sigleCours.toUpperCase().includes(filtre.cours.toUpperCase()));

        if (role !== "etudiant") {
            filtreItems = filtreItems.filter(item =>
                item.codePermanentEtudiant.toUpperCase().includes(filtre.etudiant.toUpperCase())
                || item.nomEtudiant.toUpperCase().includes(filtre.etudiant.toUpperCase()));
        }

        if (role !== "enseignant") {
            filtreItems = filtreItems.filter(item =>
                    item.nomEnseignant.toUpperCase().includes(filtre.enseignant.toUpperCase())
                || item.matriculeEnseignant?.toUpperCase().includes(filtre.enseignant.toUpperCase()))
        }
        setListeDREFiltree(filtreItems);
    }, [listeDRE, role]);

    const trier = (index) => {
        setColonnes(
            colonnes.map((colonne, i) => ({
                ...colonne,
                active: index === i,
                order:
                    (index === i &&
                        (colonne.order === 'desc' ? 'asc' : 'desc')) ||
                    undefined
            }))
        );
        setListeDREFiltree([...listeDREFiltree].sort(
            comparator(
                colonnes[index].prop,
                colonnes[index].order === 'desc'
            )
        ));
    };

    return (
        <MiseEnPage>
            <SectionFormulaire
                title={'FILTRE'}>
                <FiltreListeDRE
                    statuts={STATUTS}
                    filtrer={filtrer}
                    role={role}
                />
            </SectionFormulaire>
            <SectionFormulaire
                title={'LISTE DES DEMANDES'}>
                {enChargement
                    ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                        <CircularProgress/>
                    </Box>
                    : fetchError
                        ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                            <h3 style={{color: 'red'}}>{fetchError}</h3>
                        </Box>
                        : listeDREFiltree.length > 0
                            ?
                            <TableListeDRE
                                items={listeDREFiltree}
                                colonnes={colonnes}
                                trier={trier}
                                role={role}
                            />

                            : <Box sx={{display: 'flex', justifyContent: 'center'}}>
                                <h3>Aucune demande à afficher.</h3>
                            </Box>
                }
            </SectionFormulaire>
        </MiseEnPage>
    );
}