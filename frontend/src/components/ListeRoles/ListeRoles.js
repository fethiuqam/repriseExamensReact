import ListeAdmin from "../ListeAdmin/ListeAdmin";
import ChipBox from "../ChipBox/ChipBox";
import GridMultiSelect from "../GridMultiSelect/GridMultiSelect";
import {useEffect, useState} from "react";
import * as apiClient from "../../api/ApiClient"

export default function ListRoles() {

    const [permissions, setPermissions] = useState([]);

    useEffect(() => {
            apiClient.get("/api/permissions/").then(response => setPermissions(response));
        },
        [setPermissions]
    )

    return <ListeAdmin
        titre="Roles"
        getEndpoint="roles"
        colonnes={
            [
                {
                    field: "nom",
                    headerName: "Nom",
                    editable: true,
                    default: ""
                },
                {
                    field: "permissions",
                    headerName: "Permissions",
                    flex: 1,
                    editable: true,
                    default: [],
                    renderCell: ({value}) => value && <ChipBox labels={value}/>,
                    renderEditCell: props => <GridMultiSelect
                        {...props}
                        items={permissions}
                        renderValue={(permissions) => <ChipBox labels={permissions}/>}
                    />
                }

            ]}

    />

}
