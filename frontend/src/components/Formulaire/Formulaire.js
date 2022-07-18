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

    const { id, jwt } = useContext(AuthContext);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();
    const [error, setError] = useState();

    const [etudiant, setEtudiant] = useState({
        nom: "",
        prenom: "",
        codePermanent: "",
        coursGroupes: []
    });

    // Déclaration des variables qui seront utilisées dans le react hook form
    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues,
        control
    } = useForm();

    // Déclaration de la fonction pour récupérer un étudiant
    useEffect(() => {
        const API_URL = `/api/etudiants/${id}`;
        const fetchItems = async () => {
            try {
                const reponse = await fetch(API_URL,
                    {
                        method: 'get',
                        headers: { 'Authorization': 'Bearer ' + jwt }
                    });
                const data = await reponse.json();
                // On place les données reçues dans le state
                setEtudiant(data);
            } catch (err) {
                setError(err.message);
            }
        }
        // Appel de la fonction pour récupérer un étudiant
        fetchItems().catch(console.error);
    }, [id, jwt])

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

    // Gère la soumission d'une DRE
    async function handleFormSubmit(data) {
        try {
            setIsSubmitting(true);
            const coursGroupe = etudiant.coursGroupes.filter(e => e.id === data.sigleCours)[0];
            // On lie le formulaire, l'étudiant et son cours groupe avant d'envoyer le data
            data = { ...data, etudiant, coursGroupe };
            formData.append('nouvelleDemande',
                new Blob([JSON.stringify(data)], { type: "application/json" }));

            var response = await fetch("/api/demandes", {
                method: "POST",
                body: formData,
                headers: { 'Authorization': 'Bearer ' + jwt }
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
                        texte="ERREUR : Votre demande n'a pas pu être envoyée."
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
                                <th>Prénom</th>
                                <th>{etudiant.prenom}</th>
                            </tr>
                            <tr>
                                <th>Code permanent</th>
                                <th>{etudiant.codePermanent}</th>
                            </tr>
                        </tbody>
                    </table>
                </SectionFormulaire>
                <SectionFormulaire title={'INFORMATIONS SUR L\'ABSENCE'}>
                    <Controller control={control}
                        name="coursGroupeEnseignant"
                        render={({ field: { onChange } }) => {
                            return (
                                <Grid container spacing={2} className="affichageInfosTable">
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={4}>
                                                <InputLabel id="etiquette-signelCours">Sigle du cours</InputLabel>
                                            </Grid>
                                            <Grid item xs={8}>
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
                                                            <MenuItem value={e.id}>{e.cours.sigle} — {e.cours.nom}</MenuItem>
                                                        );
                                                    })};
                                                </Select>
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={2}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={6}>
                                                <InputLabel id="etiquette-groupeCours">Groupe</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                {etudiant.coursGroupes.filter(e => e.id === getValues("sigleCours")).map(coursFiltre => {
                                                    return (
                                                        <TextField
                                                            data-testid="groupeCoursTestId"
                                                            defaultValue=""
                                                            value={coursFiltre.groupe}
                                                            className="inputStandard"
                                                            labelid="etiquette-groupeCours"
                                                            id="groupeCours-input"
                                                            name="groupeCours"
                                                            style={{
                                                                width: "90%"
                                                            }}
                                                            InputProps={{
                                                                readOnly: true,
                                                                disableUnderline: true
                                                            }}
                                                            {...register("groupeCours", { required: true, onChange: onChange })}
                                                            error={Boolean(errors.groupeCours)}
                                                        />
                                                    )
                                                })}
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={6}>
                                                <InputLabel id="etiquette-enseignant">Enseignant</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                {etudiant.coursGroupes.filter(e => e.id === getValues("sigleCours")).map(coursFiltre => {
                                                    return (
                                                        <TextField
                                                            data-testid="enseignantTestId"
                                                            defaultValue="Enseignant"
                                                            value={coursFiltre.enseignant.nom + ' ' + coursFiltre.enseignant.prenom}
                                                            className="inputStandard"
                                                            labelid="etiquette-enseignant"
                                                            id="enseignant-input"
                                                            name="enseignant"
                                                            style={{
                                                                width: "90%"
                                                            }}
                                                            InputProps={{
                                                                readOnly: true,
                                                                disableUnderline: true
                                                            }}
                                                            {...register("enseignant", { required: true })}
                                                            error={Boolean(errors.enseignant)}
                                                        />
                                                    )
                                                })}
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    <Grid item xs={3}>
                                        <Grid container className="sectionFormulaireDivContenuRangee">
                                            <Grid item xs={6}>
                                                <InputLabel id="etiquette-descriptionExamen">Type d'examen</InputLabel>
                                            </Grid>
                                            <Grid item xs={6}>
                                                <Select
                                                    defaultValue=""
                                                    className="inputStandard"
                                                    id="descriptionExamen-input"
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
                    <Controller control={control}
                        name="dateDebutEtFinAbsence"
                        render={({ field: { onChange } }) => {
                            return (
                                <Grid container spacing={2} className="affichageInfosTable sectionFormulaireDivContenuRangee">
                                    <Grid item xs={3}>
                                        <TextField
                                            id="dateDebut"
                                            label="Début de l'absence"
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
                    <br />
                </SectionFormulaire>
                <SectionFormulaire title={'JUSTIFICATION DE L\'ABSENCE'}>
                    <div>
                        <div className="sectionFormulaireDivContenuRangee">
                            <div style={{marginRight: 10}}>
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
                                    <MenuItem value={'DECES'}>Décès</MenuItem>
                                    <MenuItem value={'ACCIDENT'}>Accident</MenuItem>
                                    <MenuItem value={'JUDICIAIRE'}>Convocation à un tribunal</MenuItem>
                                    <MenuItem value={'RELIGIEUX'}>Motif religieux</MenuItem>
                                    <MenuItem value={'AUTRE'}>Autre</MenuItem>
                                </Select>
                            </div>
                        </div>
                        <div className="sectionFormulaireDivContenuRangee">
                            <TextField
                                id="explications-input"
                                name="absenceDetails"
                                label="Explications détaillées"
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
                        <div className="sectionFormulaireDivContenuRangee">Types de fichiers acceptés : JPEG, PNG et PDF </div>
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
                                            <h3>Glisser les pièces jointes pour ajouter des preuves justificatives</h3>
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
                            disabled={isSubmitting}
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
