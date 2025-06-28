import { createContext, useContext, useEffect, useState } from "react";
import type { User } from "./models";
import { jwtDecode } from "jwt-decode";
import login from "./api/login";

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  logout: () => void;
  login: (username: string, password: string) => Promise<void>;
}

interface AuthProviderProps {
  children: React.ReactNode;
}

interface UserToken {
  sub: string;
  preferred_username: string;
  email: string;
  roles: string[];
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  const _setUserFrom = (token: string) => {
    const user = jwtDecode<UserToken>(token);
    setUser({
      id: user.sub,
      name: user.preferred_username,
      email: user.email,
      roles: user.roles,
    });

    localStorage.setItem("token", token)
  }

  const _removeToken = () => localStorage.removeItem("token");

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      _setUserFrom(token);
      // TODO: token to authorization header for axios requests?
    }
    setLoading(false);
  }, []);

  const authenticate = async (username: string, password: string) => {
    try {
      const { data } = await login(username, password);
      _setUserFrom(data.token);
    } catch (error) {
      _removeToken();
      throw error;
    }
  }

  const logout = () => {
    _removeToken();
    setUser(null);
  }

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{
      user,
      isAuthenticated: !!user,
      logout,
      login: authenticate,
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
