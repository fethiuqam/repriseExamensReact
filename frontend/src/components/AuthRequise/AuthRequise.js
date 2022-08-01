import {useContext} from "react";
import { useLocation, Navigate, Outlet } from "react-router-dom";
import AuthContext from "../../context/AuthProvider";

const AuthRequise = ({ typesPermis, permissionsRequises }) => {
    const { auth, setAuth, type, setType, setId, permissions, setPermissions } = useContext(AuthContext);
    const location = useLocation();

    const sessionPerms = sessionStorage.getItem("Permissions");
    const sessionType = sessionStorage.getItem("Type");
    const sessionId= sessionStorage.getItem("Id");
    const permissionsSuffisantes =
        !permissionsRequises ||
        permissionsRequises.every((p) => (sessionPerms ?? permissions).includes(p));

    if(!auth && sessionId && sessionType && sessionPerms != null)
        validerCookie();
     else
     {
        return (
             typesPermis?.includes(type) && permissionsSuffisantes
                 ? <Outlet />
                 : auth
                     ? <Navigate to="/non-autorise" state={{ from: location }} replace />
                     : <Navigate to="/connexion" state={{ from: location }} replace />
         );
     }

    async function validerCookie(){
        const response = await fetch("/api/validCookie",
        {
           method: 'get',
           credentials: 'include'
        });
        if (response.ok) {
            setAuth(true);
            setType(sessionStorage.getItem("Type"));
            setId(sessionStorage.getItem("Id"));
            setPermissions(sessionStorage.getItem("Permissions"));
       }
    }
}

export default AuthRequise;
