import '../../styles/StyleEtudiant.css'
import React, {useContext, useEffect, useState} from 'react';
import AuthContext from "../../context/AuthProvider";
import {Link, useParams} from "react-router-dom";
import MiseEnPage from "../MiseEnPage/MiseEnPage";
import SectionFormulaire from '../SectionFormulaire/SectionFormulaire';
import Box from "@mui/material/Box";
import {Button, CircularProgress, Container, Stack} from "@mui/material";
import JugerDRE from "../JugerDRE/JugerDRE";
import TableInfosEnseignant from '../TableInfoEnseignant/TableInforEnseignant';
import TableDetailsAbsence from '../TableDetailsAbsence/TableDetailsAbsence';
import TableInfosEtudiant from '../TableInfosEtudiant/TableInfosEtudiant';
import TableInfosDemande from '../TableInfosDemande/TableInfosDemande';
import KeyboardReturnIcon from "@mui/icons-material/KeyboardReturn";
import MessagesDRE from "../MessagesDRE/MessagesDRE";
import HistoriqueEtudiant from "../HistoriqueEtudiant/HistoriqueEtudiant";

export default function DetailsDRE() {

    const {type, permissions} = useContext(AuthContext);
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
                    credentials: 'include'
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
    }, [idDRE]);

    return (
        <MiseEnPage titre={"Détails d'une demande"}>
            {enChargement
                ? <Box sx={{display: 'flex', justifyContent: 'center'}}>
                    <CircularProgress/>
                </Box>
                : <>
                    {dre.etudiant
                        ? <SectionFormulaire title={'INFORMATIONS PERSONNELLES'}>
                            <TableInfosEtudiant etudiant={dre.etudiant}/>
                        </SectionFormulaire>
                        : null
                    }
                    {dre.enseignant
                        ? <SectionFormulaire title={'INFORMATIONS ENSEIGNANT'}>
                            <TableInfosEnseignant enseignant={dre.enseignant}/>
                        </SectionFormulaire>
                        : null
                    }

                    <SectionFormulaire title={'INFORMATIONS DEMANDE'}>
                        <TableInfosDemande dre={dre}/>
                    </SectionFormulaire>

                    {type === "enseignant"
                        ? null
                        : <SectionFormulaire title={'DETAILS'}>
                            <TableDetailsAbsence dre={dre}/>
                        </SectionFormulaire>
                    }

                    {type === "personnel"
                        ? <HistoriqueEtudiant idEtudiant={dre.etudiant.id}/>
                        : null
                    }

                    {dre.listeMessage
                        ? <MessagesDRE
                            messages={dre.listeMessage}
                            typeUtilisateur={type}
                            idDRE={idDRE}
                            actualiserDRE={checherDRE}
                        />
                        : null
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