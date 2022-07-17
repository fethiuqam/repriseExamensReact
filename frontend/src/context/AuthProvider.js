import { createContext, useState } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState(false);
    const [type, setType] = useState(null);
    const [id, setId] = useState(null);
    const [permissions, setPermissions] = useState([]);
    const [jwt, setJwt] = useState(null);

    return (
        <AuthContext.Provider value={{
            auth, setAuth,
            type, setType,
            id, setId,
            permissions, setPermissions,
            jwt, setJwt
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;
