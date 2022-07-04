import React, {useState, useEffect, useContext} from 'react';
import {
    Button,
    Checkbox, Chip,
    FormControl,
    InputLabel,
    ListItemText,
    MenuItem,
    Select, Stack, TextField
} from "@mui/material";
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import Box from "@mui/material/Box";
import AuthContext from "../../context/AuthProvider";

export default function FiltreListeDRE({statuts, filtrer}) {

    const {role} = useContext(AuthContext);

    const filtreInitial = {
        statuts: statuts,
        etudiant: '',
        enseignant: '',
        cours: ''
    }

    const [filtre, setFiltre] = useState(filtreInitial);

    useEffect(() => {
        filtrer(filtre);
    }, [filtre, filtrer])

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
                                <Chip key={value} label={value}/>
                            ))}
                        </Box>
                    )}
                >
                    {statuts.map((statut, index) => (
                        <MenuItem key={index} value={statut}>
                            <Checkbox checked={filtre.statuts.indexOf(statut) > -1}/>
                            <ListItemText primary={statut}/>
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
            <Stack spacing={2} justifyContent="space-between">
                <Stack direction="row" spacing={2}>
                    {role === "etudiant"
                        ? null
                        :
                        <TextField
                            label="Par étudiant"
                            variant="standard"
                            onChange={(e) => setFiltre({...filtre, etudiant: e.target.value})}
                            value={filtre.etudiant}
                        />
                    }
                    {role === "enseignant"
                        ? null
                        :
                        <TextField
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