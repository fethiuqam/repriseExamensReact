import '../../styles/StyleEtudiant.css'
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire"
import { TextField, InputLabel, Select } from "@mui/material";
import { Grid, Typography, MenuItem } from "@mui/material";
import { useForm, Controller } from "react-hook-form";
import Button from '../BoutonFormulaire/BoutonFormulaire';
import MiseEnPage from '../MiseEnPage/MiseEnPage';
import { useState, useEffect, useContext } from "react";
import { useNavigate } from 'react-router-dom';
import * as React from "react";
import Banniere from '../Banniere/Banniere';
import AuthContext from "../../context/AuthProvider";
import Box from '@mui/material/Box';
import AttachmentIcon from '@mui/icons-material/Attachment';
import DeleteIcon from '@mui/icons-material/Delete';
import { FileUploader } from "react-drag-drop-files";

const Formulaire = () => {

    const { id } = useContext(AuthContext);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();
    const [error, setError] = useState();

    const [etudiant, setEtudiant] = useState({
        nom: "",
        prenom: "",
        codePermanent: "",
        coursGroupes: []
    });

    // examen a ajouter dans le tableau des examens
    const [ligneExamenAreprendreCourant, setLigneExamenAreprendreCourant] = useState({
        descriptionExamen: "",
        sigleCours: "",
        groupeCours: "",
        enseignant: ""
    })
    // la liste des examens a reprendre 
    const [listeLigneExamenAreprendre, setListeLigneExamenAreprendre] = useState([]);

    const ajouterNouvelleLigneExam = () => {
        try {
            setLigneExamenAreprendreCourant({
                descriptionExamen: getValues("descriptionExamen"),
                sigleCours: getValues("sigleCours"),
                groupeCours: document.getElementById('groupeCours-input').value,
                enseignant: document.getElementById('enseignant-input').value
            })
        } catch (e) {

        }

    }

    useEffect(() => {
        if (ligneExamenAreprendreCourant.groupeCours !== ""
            && ligneExamenAreprendreCourant.enseignant !== ""
            && ligneExamenAreprendreCourant.sigleCours !== ""
            && ligneExamenAreprendreCourant.descriptionExamen !== "") {
            setListeLigneExamenAreprendre([...listeLigneExamenAreprendre, ligneExamenAreprendreCourant]);
        }

    }, [ligneExamenAreprendreCourant]); // eslint-disable-line


    // D??claration des variables qui seront utilis??es dans le react hook form
    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues,
        control
    } = useForm();


    // D??claration de la fonction pour r??cup??rer un ??tudiant
    useEffect(() => {
        const API_URL = `/api/etudiants/${id}`;
        const fetchItems = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: 'get',
                        credentials: 'include'
                    });
                const data = await reponse.json();
                // On place les donn??es re??ues dans le state
                setEtudiant(data);
            } catch (err) {
                setError(err.message);
            }
        }
        // Appel de la fonction pour r??cup??rer un ??tudiant
        fetchItems().catch(console.error);
    }, [id])

    let formData = new FormData();

    // states pour la gestion des pieces jointes
    const fileTypes = ["JPEG", "PNG", "PDF", "JPG"];
    const [files, setFiles] = useState([]);

    /***
     * Ajoute des nouveaux fichiers
     * @param {*} nouveauFichiers les nouveaux fichiers a ajouter
     */
    const handleChangeFile = (nouveauFichiers) => {
        setFiles(ancienFichiers => [...ancienFichiers, ...nouveauFichiers]);
    };

    /**
     * Fonction appelee aux changements effectues aux fichiers
     * Notes: le commentaire eslint-disable-line etait la methode
     * la plus efficace pour enlever un warning de dependance qui
     * n'avait pas d'impact sinon
     */
    useEffect(() => {
        ajouterFilesFormdata();
    }, [files]); // eslint-disable-line

    /**
     * Fonction pour supprimer un fichier donne 
     * @param {*} nombre index du fichier a enlever
     */
    const supprimerFichier = (nombre) => {
        try {
            const nouvArray = [...files];
            nouvArray.splice(nombre, 1);
            setFiles(nouvArray);
        } catch (e) {
            console.error("Erreur dans la supression fichier")
        }
    };

    /**
     * Fonction pour supprimer une colonne de formLigneExam donne 
     * @param {*} nombre index du fichier a enlever
     */
    const supprimerFormLigneExam = (nombre) => {
        try {
            const nouvArray = [...listeLigneExamenAreprendre];
            nouvArray.splice(nombre, 1);
            setListeLigneExamenAreprendre(nouvArray);
        } catch (e) {
            console.error("Erreur dans la supression de formLigneExam")
        }
    };


    /***
     * Fonction pour faire un update des fichiers dans formdata
     * a partir des fichiers presents dans le state files
     */
    const ajouterFilesFormdata = () => {
        formData.delete("files");
        files.forEach(f => formData.append("files", f));
    }

    const onSubmit = (data) => {
        handleFormSubmit(data);
    };


    // G??re la soumission d'une DRE
    async function handleFormSubmit(data) {
        try {
            setIsSubmitting(true);
            // On lie le formulaire, l'??tudiant et son cours groupe avant d'envoyer le data
            var donnees = []
            listeLigneExamenAreprendre.forEach(examen => {
                const coursGroupe = etudiant.coursGroupes.filter(e => e.id === examen.sigleCours)[0];
                donnees.push({
                    ...examen,
                    absenceDateDebut: data.absenceDateDebut,
                    absenceDateFin: data.absenceDateFin,
                    motifAbsence: data.motifAbsence,
                    absenceDetails: data.absenceDetails,
                    etudiant,
                    coursGroupe
                })
            })
            formData.append('nouvellesDemandes',
                new Blob([JSON.stringify(donnees)], { type: "application/json" }));

            var response = await fetch("/api/demandes", {
                method: "POST",
                body: formData,
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error("Erreur du POST");
            }
            navigate("/");
        } catch (error) {
            setError(error);
            setIsSubmitting(false);
        }
    }

    return (
        <MiseEnPage titre={'Demande de reprise d\'examen'}>
            <form onSubmit={handleSubmit(onSubmit)}>
                {error &&
                    <Banniere
                        id="banniereErreur"
                        texte="ERREUR : Votre demande n'a pas pu ??tre envoy??e."
                        className="banniereErreur" />
                }
                <SectionFormulaire title={'INFORMATIONS PERSONNELLES'}>
                    <table className="affichageInfosTable">
                        <tbody>
                            <tr>
                                <th>Nom</th>
                                <th>{etudiant.nom}</th>
                            </tr>
                            <tr>
                                <th>Pr??nom</th>
                                <th>{etudiant.prenom}</th>
                            </tr>
                            <tr>
                                <th>Code permanent</th>
                                <th>{etudiant.codePermanent}</th>
                            </tr>
                        </tbody>
                    </table>
                </SectionFormulaire>
                <SectionFormulaire title={'EXAMENS ?? REPRENDRE'}>
                    <Controller control={control}
                        name="coursGroupeEnseignant"
                        render={({ field: { onChange } }) => {
                            return (
                                <Grid container spacing={2} className="affichageInfosTable">
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={5}>
                                                <InputLabel id="etiquette-signelCours">Sigle du cours</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <Select
                                                    data-testid="sigleCoursTestId"
                                                    defaultValue=""
                                                    className="inputStandard"
                                                    labelId="etiquette-signelCours"
                                                    id="sigleCours-input"
                                                    name="sigleCours"
                                                    label="Sigle du cours"
                                                    style={{
                                                        width: "90%"
                                                    }}
                                                    {...register("sigleCours", { required: true, onChange: onChange })}
                                                    error={Boolean(errors.sigleCours)}
                                                >
                                                    {Object.values(etudiant.coursGroupes).map(e => {
                                                        return (
                                                            <MenuItem value={e.id} >{e.cours.sigle} ??? {e.cours.nom}</MenuItem>
                                                        );
                                                    })};
                                                </Select>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={3}>
                                                <InputLabel id="etiquette-groupeCours">Groupe</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <TextField
                                                    data-testid="groupeCoursTestId"
                                                    placeholder='ex: 020'
                                                    value={etudiant.coursGroupes.filter(e => e.id === getValues("sigleCours")).map(coursFiltre => coursFiltre.groupe)}
                                                    labelid="etiquette-groupeCours"
                                                    id="groupeCours-input"
                                                    name="groupeCours"
                                                    InputProps={{
                                                        readOnly: true,
                                                    }}
                                                />
                                            </Grid>

                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={4}>
                                                <InputLabel id="etiquette-enseignant">Enseignant</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <TextField
                                                    data-testid="enseignantTestId"
                                                    placeholder="ex: Melanie Lord"
                                                    value={etudiant.coursGroupes.filter(e => e.id === getValues("sigleCours")).map(coursFiltre =>
                                                        coursFiltre.enseignant.nom + ' ' + coursFiltre.enseignant.prenom)}
                                                    labelid="etiquette-enseignant"
                                                    id="enseignant-input"
                                                    name="enseignant"
                                                    InputProps={{
                                                        readOnly: true,
                                                    }}
                                                />
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={5}>
                                                <InputLabel id="etiquette-descriptionExamen">Type d'examen</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <Select
                                                    defaultValue=""
                                                    className="inputStandard"
                                                    id="descriptionExamen-input"
                                                    variant="outlined"
                                                    style={{
                                                        width: "90%"
                                                    }}
                                                    {...register("descriptionExamen", { required: true })}
                                                    error={Boolean(errors.descriptionExamen)}
                                                >
                                                    <MenuItem value={'examen intra'}>INTRA</MenuItem>
                                                    <MenuItem value={'examen final'}>FINAL</MenuItem>
                                                </Select>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            );
                        }}
                    />
                    <br />
                    <Button
                        variant="outlined"
                        onClick={() => {
                            ajouterNouvelleLigneExam()
                        }}
                    >
                        ajouter
                    </Button>
                    <br></br>
                    <br></br>
                    <h4>Liste des examens ?? reprendre</h4>
                    <div>
                        <Grid container spacing={2}>
                            <Grid item xs={2}>
                                <InputLabel id="etiquette-descriptionExamen">Type d'examen</InputLabel>
                            </Grid>
                            <Grid item xs={2}>
                                <InputLabel id="etiquette-sigleCours">Sigle du cours</InputLabel>
                            </Grid>
                            <Grid item xs={2}>
                                <InputLabel id="etiquette-groupeCours">Groupe</InputLabel>
                            </Grid>
                            <Grid item xs={2}>
                                <InputLabel id="etiquette-enseignant">Enseignant</InputLabel>
                            </Grid>
                        </Grid>
                        <ul className='noStyleList'>
                            {listeLigneExamenAreprendre.map(function (item, idx) {
                                return (
                                    <li key={idx} >
                                        <Grid container>
                                            <Grid item xs={2}>
                                                {item.descriptionExamen}
                                            </Grid>
                                            <Grid item xs={2}>
                                                {etudiant.coursGroupes.filter(e => e.id === item.sigleCours).map(coursFiltre =>
                                                    coursFiltre.cours.sigle + '???' + coursFiltre.cours.nom)}
                                            </Grid>
                                            <Grid item xs={2}>
                                                {item.groupeCours}
                                            </Grid>
                                            <Grid item xs={2}>
                                                {item.enseignant}
                                            </Grid>
                                            <Grid item xs={1}>
                                                <Button
                                                    className="buttonSupprimerFichier"
                                                    startIcon={<DeleteIcon />}
                                                    disableElevation
                                                    color="error"
                                                    size="small"
                                                    onClick={() => {
                                                        supprimerFormLigneExam(idx);
                                                    }}
                                                >
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </li>)
                            })}
                        </ul>
                    </div>
                </SectionFormulaire>
                <SectionFormulaire title={'INFORMATIONS ET JUSTIFICATION DE L\'ABSENCE'}>
                    <Controller control={control}
                        name="dateDebutEtFinAbsence"
                        render={({ field: { onChange } }) => {
                            return (
                                <Grid container spacing={2} className="affichageInfosTable sectionFormulaireDivContenuRangee">
                                    <Grid item xs={3}>
                                        <TextField
                                            id="dateDebut"
                                            label="D??but de l'absence"
                                            name="absenceDateDebut"
                                            type="date"
                                            fullWidth
                                            InputProps={{
                                                inputProps: {
                                                    min: new Date(new Date().getFullYear(), new Date().getMonth() - 3, new Date().getDate()).toISOString().slice(0, 10),
                                                    max: getValues("absenceDateFin")
                                                }
                                            }}
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                            defaultValue={new Date().toISOString().slice(0, 10)}
                                            {...register("absenceDateDebut", {
                                                required: "Ce champs est requis.",
                                                onChange: onChange
                                            })}
                                            error={Boolean(errors.absenceDateDebut)}
                                        />
                                    </Grid>
                                    <Grid item xs={3}>
                                        <TextField
                                            id="dateFin"
                                            label="Fin de l'absence"
                                            name="absenceDateFin"
                                            type="date"
                                            fullWidth
                                            InputProps={{
                                                inputProps: {
                                                    min: getValues("absenceDateDebut")
                                                }
                                            }}
                                            InputLabelProps={{
                                                shrink: true,
                                            }}
                                            defaultValue=""
                                            {...register("absenceDateFin", {
                                                required: "Ce champs est requis.",
                                                onChange: onChange
                                            })}
                                            error={Boolean(errors.absenceDateFin)}
                                        />
                                    </Grid>
                                </Grid>
                            )
                        }} />
                    <div>
                        <div className="sectionFormulaireDivContenuRangee">
                            <div style={{ marginRight: 10 }}>
                                <InputLabel id="etiquette-motif-absence">Motif</InputLabel>
                            </div>
                            <div>
                                <Select
                                    data-testid="motifTestId"
                                    defaultValue=""
                                    className="inputStandard"
                                    labelId="etiquette-motif-absence"
                                    id="menu-motif-absence"
                                    name="motifAbsence"
                                    label="Motif"
                                    {...register("motifAbsence", { required: true })}
                                    error={Boolean(errors.motifAbsence)}
                                >
                                    <MenuItem value={'MEDICAL'}>Hospitalisation</MenuItem>
                                    <MenuItem value={'DECES'}>D??c??s</MenuItem>
                                    <MenuItem value={'ACCIDENT'}>Accident</MenuItem>
                                    <MenuItem value={'JUDICIAIRE'}>Convocation ?? un tribunal</MenuItem>
                                    <MenuItem value={'RELIGIEUX'}>Motif religieux</MenuItem>
                                    <MenuItem value={'AUTRE'}>Autre</MenuItem>
                                </Select>
                            </div>
                        </div>
                        <div className="sectionFormulaireDivContenuRangee">
                            <TextField
                                id="explications-input"
                                name="absenceDetails"
                                label="Explications d??taill??es"
                                type="text"
                                multiline
                                fullWidth
                                minRows={4}
                                variant="outlined"
                                {...register("absenceDetails", { required: "Ce champs est requis." })}
                                error={Boolean(errors.absenceDetails)}
                            />
                        </div>
                        <div className="textColor sectionFormulaireDivContenuRangee">
                            <Typography>Justificatifs</Typography>
                        </div>
                        <div className="sectionFormulaireDivContenuRangee">Types de fichiers accept??s : JPEG, PNG et PDF </div>
                        <div className="sectionFormulaireDivContenuRangee">
                            <FileUploader
                                multiple={true}
                                handleChange={handleChangeFile}
                                onTypeError={(err) => console.log("Erreur type fichier")}
                                name="file"
                                types={fileTypes}
                                label="Deposez vos fichiers ici"
                                fileOrFiles={null}
                                children={
                                    <Box component="div" sx={{
                                        width: 800,
                                        height: 200,
                                        p: 1,
                                        border: '1px dashed grey',
                                        '&:hover': {
                                            backgroundColor: 'primary.main',
                                            opacity: [0.6, 0.5, 0.4],
                                        },
                                    }}>
                                        <div className="center">
                                            <h3>Glisser les pi??ces jointes pour ajouter des preuves justificatives</h3>
                                        </div>
                                    </Box>
                                }
                            />
                        </div>
                        <div>
                            <ul className="afficherFichiers">
                                {files.map(function (file, idx) {
                                    return (
                                        <li key={idx}>
                                            <AttachmentIcon></AttachmentIcon>
                                            {file.name}
                                            <span className='divSupprimerFichierButton'>
                                                <Button
                                                    className="buttonSupprimerFichier"
                                                    startIcon={<DeleteIcon />}
                                                    disableElevation
                                                    color="error"
                                                    size="small"
                                                    onClick={() => {
                                                        supprimerFichier(idx);
                                                    }}
                                                >
                                                </Button>
                                            </span>
                                            <br />
                                        </li>)
                                })}
                            </ul>
                        </div>
                    </div>
                </SectionFormulaire>
                <div>
                    <div className="boutonsFormulaire">
                        <Button
                            variant="outlined"
                            onClick={() => {
                                navigate("/");
                            }}
                        >
                            Annuler
                        </Button>
                        <Button
                            id="boutonSoumettre"
                            variant="contained"
                            disableElevation
                            type="submit"
                            disabled={listeLigneExamenAreprendre.length === 0? true: isSubmitting}
                        >
                            Soumettre
                        </Button>
                    </div>
                </div>
            </form>
        </MiseEnPage >
    );
}

export default Formulaire;
