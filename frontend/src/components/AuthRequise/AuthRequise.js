import {useContext} from "react";
import { useLocation, Navigate, Outlet } from "react-router-dom";
import AuthContext from "../../context/AuthProvider";

const AuthRequise = ({ typesPermis }) => {
    const { auth, type } = useContext(AuthContext)
    const location = useLocation();

    return (
        typesPermis?.includes(type)
            ? <Outlet />
            : auth
                ? <Navigate to="/non-autorise" state={{ from: location }} replace />
                : <Navigate to="/connexion" state={{ from: location }} replace />
    );
}

export default AuthRequise;
