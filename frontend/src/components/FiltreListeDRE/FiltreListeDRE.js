import React, {useContext} from 'react';
import {Button, Checkbox, FormControl, InputLabel, MenuItem, Select, Stack, TextField} from "@mui/material";
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import Box from "@mui/material/Box";
import Statut from "../Statut/Statut";
import {statutsUtilisteurs} from "../../utils/utils";
import AuthContext from "../../context/AuthProvider";
import {TypeId} from "../../utils/const";


export default function FiltreListeDRE({filtre, setFiltre}) {

    const {type} = useContext(AuthContext);

    const filtreInitial = {
        statuts: statutsUtilisteurs(type),
        etudiant: '',
        enseignant: '',
        cours: ''
    }

    return (
        <Stack direction="row" spacing={2} justifyContent="space-between">
            <FormControl variant="standard" sx={{m: 1, width: 500}}>
                <InputLabel id="demo-multiple-checkbox-label">par statut</InputLabel>
                <Select
                    data-testid="filtre-statut"
                    labelId="demo-multiple-checkbox-label"
                    id="demo-multiple-checkbox"
                    multiple
                    value={filtre.statuts}
                    onChange={(e) => setFiltre({...filtre, statuts: e.target.value})}
                    renderValue={(selected) => (
                        <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                            {selected.map((value) => (
                                <Statut key={value} statut={value}/>
                            ))}
                        </Box>
                    )}
                >
                    {statutsUtilisteurs(type).map((statut, index) => (
                        <MenuItem key={index} value={statut}>
                            <Checkbox checked={filtre.statuts.indexOf(statut) > -1}/>
                            <Statut statut={statut}/>
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            <Stack spacing={2} justifyContent="space-between">
                <Stack direction="row" spacing={2}>
                    {type !== TypeId.Etudiant
                        && <TextField
                            label="Par étudiant"
                            variant="standard"
                            onChange={(e) => setFiltre({...filtre, etudiant: e.target.value})}
                            value={filtre.etudiant}
                        />
                    }
                    {type !== TypeId.Enseignant
                        && <TextField
                            label="Par enseignant"
                            variant="standard"
                            onChange={(e) => setFiltre({...filtre, enseignant: e.target.value})}
                            value={filtre.enseignant}
                        />
                    }
                    <TextField
                        label="Par cours"
                        variant="standard"
                        onChange={(e) => setFiltre({...filtre, cours: e.target.value})}
                        value={filtre.cours}
                    />
                </Stack>
                <Stack direction="row" justifyContent="flex-end">
                    <Button
                        variant="outlined"
                        endIcon={<RestartAltIcon/>}
                        onClick={() => setFiltre(filtreInitial)}
                    >
                        Réinitialiser le filtre
                    </Button>
                </Stack>

            </Stack>
        </Stack>
    )
}
