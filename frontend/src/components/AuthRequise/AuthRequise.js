import {useContext} from "react";
import { useLocation, Navigate, Outlet } from "react-router-dom";
import AuthContext from "../../context/AuthProvider";

const AuthRequise = ({ rolesPermis }) => {
    const { auth, role } = useContext(AuthContext)
    const location = useLocation();

    return (
        rolesPermis?.includes(role)
            ? <Outlet />
            : auth
                ? <Navigate to="/non-autorise" state={{ from: location }} replace />
                : <Navigate to="/connexion" state={{ from: location }} replace />
    );
}

export default AuthRequise;