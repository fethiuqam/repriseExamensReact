import {useContext} from "react";
import { useLocation, Navigate, Outlet } from "react-router-dom";
import AuthContext from "../../context/AuthProvider";

const AuthRequise = ({ typesPermis, permissionsRequises }) => {
    const { auth, type, permissions } = useContext(AuthContext)
    const location = useLocation();

    const permissionsSuffisantes = permissionsRequises ? permissionsRequises.every(p => permissions.includes(p)) : true;

    return (
        typesPermis?.includes(type) && permissionsSuffisantes
            ? <Outlet />
            : auth
                ? <Navigate to="/non-autorise" state={{ from: location }} replace />
                : <Navigate to="/connexion" state={{ from: location }} replace />
    );
}

export default AuthRequise;
