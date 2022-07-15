import '../../styles/StyleEtudiant.css'
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire"
import { TextField, InputLabel, Select, Container } from "@material-ui/core/";
import { Grid, Typography } from "@mui/material";
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




// stocke les infos des etudiant TODO : connecter avec les identifiants
const infosEtudiant = {
    nom: "Tremblay",
    prenom: "Jeanne",
    codePermanent: "TREJ90021580"
}

const Formulaire = () => {
    const {jwt} = useContext(AuthContext);
    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues,
        control
    } = useForm();

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
        } catch (e){
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

    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();
    const [error, setError] = useState();

    async function handleFormSubmit(data) {

        try {
            setIsSubmitting(true);

            formData.append('nouvelleDemande',
                new Blob([JSON.stringify(data)], { type: "application/json" }));
            
            var response = await fetch("/api/demandes", {
                method: "POST",
                body: formData,
                headers: { 'Authorization':'Bearer ' + jwt }
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

    const onSubmit = (data) => {
        handleFormSubmit(data);
    };


    return (
        <>
            <MiseEnPage titre={'Demande de reprise d\'examen'}
                children={
                    <form onSubmit={handleSubmit(onSubmit)}>
                        {error && 
                            <Banniere 
                                id="banniereErreur" 
                                texte="ERREUR : Votre demande n'a pas pu être envoyée."
                                className="banniereErreur"/>
                        }
                        <SectionFormulaire
                            title={'INFORMATIONS PERSONNELLES'}
                            children={
                                <>
                                    <table className="affichageInfosTable">
                                        <tbody>
                                            <tr>
                                                <th>Nom</th>
                                                <th>{infosEtudiant.nom}</th>
                                            </tr>
                                            <tr>
                                                <th>Prénom</th>
                                                <th>{infosEtudiant.prenom}</th>
                                            </tr>
                                            <tr>
                                                <th>Code permanent</th>
                                                <th>{infosEtudiant.codePermanent}</th>
                                            </tr>
                                        </tbody>
                                    </table>
                                </>
                            }
                        />
                        <SectionFormulaire
                            title={'INFORMATIONS SUR L\'ABSENCE'}
                            children={
                                <>
                                    <Grid container spacing={2} className="affichageInfosTable">
                                        <Grid item xs={3}>
                                            <Grid container>
                                                <Grid item xs={4}>
                                                    <InputLabel id="etiquette-signelCours">Sigle du
                                                        cours</InputLabel>
                                                </Grid>
                                                <Grid item xs={8}>
                                                    <Select
                                                        data-testid="sigleCoursTestId"
                                                        native
                                                        defaultValue=""
                                                        className="inputStandard"
                                                        labelId="etiquette-signelCours"
                                                        id="sigleCours-input"
                                                        name="sigleCours"
                                                        label="Sigle du cours"
                                                        style={{
                                                            width: "90%"
                                                        }}
                                                        {...register("sigleCours", { required: true })}
                                                        error={Boolean(errors.sigleCours)}
                                                    >
                                                        <option name={'INM5151'} value={'INM5151'}>INM5151</option>
                                                        <option value={'INF6150'}>INF6150</option>
                                                        <option value={'INF5171'}>INF5171</option>
                                                    </Select>
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                        <Grid item xs={2}>
                                            <Grid container>
                                                <Grid item xs={4}>
                                                    <InputLabel id="etiquette-groupeCours">Groupe</InputLabel>
                                                </Grid>
                                                <Grid item xs={8}>
                                                    <TextField // TODO va être rempli automatiquement quand on choisit le sigle
                                                        data-testid="groupeCoursTestId"
                                                        defaultValue=""
                                                        className="inputStandard"
                                                        labelid="etiquette-groupeCours"
                                                        id="groupeCours-input"
                                                        name="groupeCours"
                                                        label="Groupe"
                                                        style={{
                                                            width: "90%"
                                                        }}
                                                        {...register("groupeCours", { required: true })}
                                                        error={Boolean(errors.groupeCours)}
                                                    />
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                        <Grid item xs={3}>
                                            <Grid container>
                                                <Grid item xs={4}>
                                                    <InputLabel id="etiquette-enseignant">Enseignant</InputLabel>
                                                </Grid>
                                                <Grid item xs={8}>
                                                    <TextField // TODO va être rempli automatiquement quand on choisit le sigle
                                                        data-testid="enseignantTestId"
                                                        defaultValue=""
                                                        className="inputStandard"
                                                        labelid="etiquette-enseignant"
                                                        id="enseignant-input"
                                                        name="enseignant"
                                                        label="Enseignant"
                                                        style={{
                                                            width: "90%"
                                                        }}
                                                        {...register("enseignant", { required: true })}
                                                        error={Boolean(errors.enseignant)}
                                                    />
                                                </Grid>
                                            </Grid>
                                        </Grid>
                                        <Grid item xs={3}></Grid>
                                    </Grid>
                                    <Controller control={control}
                                        name="dateDebutEtFinAbsence"
                                        render={({ field: { onChange, value } }) => {
                                            return (
                                                <Grid container spacing={2} className="affichageInfosTable">
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
                                                                    min: getValues("absenceDateDebut"),
                                                                    max: new Date().toISOString().slice(0, 10)
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
                                </>
                            }
                        />
                        <SectionFormulaire
                            title={'JUSTIFICATION DE L\'ABSENCE'}
                            children={
                                <div className="sectionFormulaireDivContenuRangee">
                                    <div>
                                        <InputLabel id="etiquette-motif-absence">Motif</InputLabel>
                                        <Select
                                            data-testid="motifTestId"
                                            native
                                            defaultValue=""
                                            className="inputStandard"
                                            labelId="etiquette-motif-absence"
                                            id="menu-motif-absence"
                                            name="motifAbsence"
                                            label="Motif"
                                            {...register("motifAbsence", { required: true })}
                                            error={Boolean(errors.motifAbsence)}
                                        >
                                            <option value={'MEDICAL'}>Hospitalisation</option>
                                            <option value={'DECES'}>Décès</option>
                                            <option value={'ACCIDENT'}>Accident</option>
                                            <option value={'JUDICIAIRE'}>Convocation à un tribunal</option>
                                            <option value={'RELIGIEUX'}>Motif religieux</option>
                                            <option value={'AUTRE'}>Autre</option>
                                        </Select>
                                    </div>
                                    <div>
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
                                    <div className="textColor">
                                        <Typography>Justificatifs</Typography>
                                    </div>
                                    <div>Types de fichiers acceptés : JPEG, PNG et PDF </div>
                                    <div>
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
                                                  },}}>
                                                <div className="center">
                                                    <h3>Glisser les pièces jointes pour ajouter des preuves justificatives</h3>
                                                </div>                                          
                                            </Box>
                                        }
                                        />
                                    </div>
                                    <div>
                                        <ul className="afficherFichiers">
                                            {files.map(function(file, idx){
                                                return (
                                                <li key={idx}>
                                                    <AttachmentIcon></AttachmentIcon>
                                                    {file.name}
                                                    <span className='divSupprimerFichierButton'>
                                                        <Button
                                                            className="buttonSupprimerFichier"
                                                            startIcon={<DeleteIcon/>}
                                                            disableElevation
                                                            color="error"
                                                            size="small"
                                                            onClick={() => {
                                                                supprimerFichier(idx);
                                                            }}
                                                        >
                                                        </Button>
                                                    </span>
                                                    <br/>
                                                </li>)
                                            })}
                                        </ul>
                                    </div>
                                </div>
                            }
                        />
                        <Container>
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
                        </Container>
                    </form>
                }
            />
        </>
    );
}

export default Formulaire;
