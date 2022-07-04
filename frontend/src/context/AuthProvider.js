import { createContext, useState } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
    const [auth, setAuth] = useState(false);
    const [role, setRole] = useState(null);
    const [id, setId] = useState(null);

    return (
        <AuthContext.Provider value={{ auth, setAuth, role, setRole, id, setId }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext;