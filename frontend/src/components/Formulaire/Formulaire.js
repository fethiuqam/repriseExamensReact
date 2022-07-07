import '../../styles/StyleEtudiant.css'
import SectionFormulaire from "../SectionFormulaire/SectionFormulaire"
import { TextField, InputLabel, Select, MenuItem, Container } from "@material-ui/core/";
import { Grid, Typography } from "@mui/material";
import { useForm, Controller } from "react-hook-form";
import Button from '../BoutonFormulaire/BoutonFormulaire';
import MiseEnPage from '../MiseEnPage/MiseEnPage';
import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import * as React from "react";

// stocke les infos des etudiant TODO : connecter avec les identifiants
const infosEtudiant = {
    nom: "Tremblay",
    prenom: "Jeanne",
    codePermanent: "TREJ90021580"
}

const Formulaire = () => {
    const {
        register,
        handleSubmit,
        formState,
        formState: { errors },
        getValues,
        control,
        reset
    } = useForm();

    let formData = new FormData();
    const onFileChange = (e) => {
        const fichiers = Array.from(e.target.files);
        if (e.target && e.target.files) {
            for (let i = 0; i < fichiers.length; i++) {
                formData.append("files", fichiers[i]);
            }
        }
    }

    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    async function handleFormSubmit(data) {

        try {
            formData.append('nouvelleDemande',
                new Blob([JSON.stringify(data)], { type: "application/json" }));
            setIsSubmitting(true);
            await fetch("/api/demandes", {
                method: "POST",
                body: formData
            });
        } catch (error) {
            alert("ERREUR : Votre demande n'a pas pu être envoyée."); // TODO remplacer quand on aura React 16/react-js-banner
        } finally {
            setIsSubmitting(false);
            navigate("/");
        }
    }

    const onSubmit = (data) => {
        handleFormSubmit(data);
    };

    useEffect(() => {
        if (formState.isSubmitSuccessful) {
            reset({
                sigleCours: "",
                groupeCours: "",
                enseignant: "",
                absenceDateDebut: "",
                absenceDateFin: "",
                motifAbsence: "",
                absenceDetails: ""
            });
        }
    }, [formState, reset]);

    return (
        <>
            <MiseEnPage titre={'Demande de reprise d\'examen'}
                children={
                    <form onSubmit={handleSubmit(onSubmit)}>
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
                                                        <MenuItem value={'INM5151'}>INM5151</MenuItem>
                                                        <MenuItem value={'INF6150'}>INF6150</MenuItem>
                                                        <MenuItem value={'INF5171'}>INF5171</MenuItem>
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
                                                        defaultValue=""
                                                        className="inputStandard"
                                                        labelid="etiquette-groupeCours"
                                                        id="groupeCours-input"
                                                        name="groupeCours"
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
                                                        defaultValue=""
                                                        className="inputStandard"
                                                        labelid="etiquette-enseignant"
                                                        id="enseignant-input"
                                                        name="enseignant"
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
                                    <div>
                                        <input
                                            style={{ display: "none" }}
                                            id="justificatifs"
                                            type="file"
                                            onChange={onFileChange}
                                            multiple
                                        />
                                        <label htmlFor="justificatifs">
                                            <Button
                                                variant="contained"
                                                disableElevation
                                                className="button"
                                                component="span">
                                                Téléverser
                                            </Button>
                                        </label>
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