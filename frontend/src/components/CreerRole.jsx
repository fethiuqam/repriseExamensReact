import "../Form.css";
import {useState} from "react";
import {Checkbox, FormControl, FormControlLabel, FormLabel, Grid} from "@material-ui/core";

function CreerRole() {
    const [nom, setName] = useState("");
    const [permissions, setPermissions] = useState([]);
    const [message, setMessage] = useState("");

    let handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let res = await fetch("/api/roles/", {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    nom: nom,
                    permissions: [permissions],
                }),

            });
            //let resJson = await res.json();
            if (res.status === 200) {
                setName("");
                setPermissions(permissions => [...permissions, `${permissions.length}`]);
                setMessage("Role created successfully");
            } else {
                setMessage("Some error occured");
            }
        } catch (err) {
            console.log(err);
        }
    };

    return (
        <Grid container alignItems="center" justifyContent="center">
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={nom}
                    placeholder="Name"
                    onChange={(e) => setName(e.target.value)}
                />
                <input
                    type="text"
                    value={permissions}
                    placeholder="Permissions"
                    onChange={(e) => setPermissions([e.target.value])}
                />

                <Grid item alignItems="bottom" justifyContent="bottom">
                    <FormControl>
                        <FormLabel>Permissions</FormLabel>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="ListerDRE"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="AfficherDRE"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="AfficherJustificatifs"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="JugerRecevabilite"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="PlanifierDates"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="GererUsagers"
                            />
                        </div>
                        <div>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        //checked={checked.permissions}
                                        //onChange={handleChange}
                                        name="checked"
                                        color="primary"
                                    />
                                }
                                label="GererRoles"
                            />
                        </div>
                    </FormControl>
                </Grid>
                <button type="submit">Creer</button>

                <div className="message">{message ? <p>{message}</p> : null}</div>
            </form>
        </Grid>

    );
}

export default CreerRole;
