import { useEffect, createContext, useContext, useState } from "react";
import { useNavigate } from "react-router";
import * as usersApi from "@/api/users";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const _setUserFrom = (token) => {
        const user = jwtDecode(token);
        setUser({
            id: user.sub,
            email: user.email,
            roles: user.roles || [],
        });
    }

    const _storeToken = (token) => localStorage.setItem("token", token);
    const _removeToken = () => localStorage.removeItem("token");

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            _setUserFrom(token);
            // TODO: token to authorization header for axios requests?
        }
        setLoading(false);
    }, []);

    const login = async (user) => {
        const response = await usersApi.login(user);
        const data = await response.json();

        if (response.status === 401) {
            _removeToken();
            throw new Error(data.message);
        } else if (response.status === 200) {
            const { token } = data;

            _storeToken(token);
            _setUserFrom(token);

            // TODO: token to authorization header for axios requests?
            navigate('/');
        }
    }

    const logout = () => {
        _removeToken();
        setUser(null);
        // TODO: remove token from authorization header for axios requests?
        navigate('/login');
    }

    return (
        <AuthContext.Provider value={{
            user,
            isAuthenticated: !!user,
            loading,
            logout,
            login,
        }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within a AuthProvider');
    }
    return context;
}
