import { useAuth } from "./AuthContext";
import { useLocation, Navigate, Outlet } from "react-router";

const ProtectedRoute = ({ requiredRoles }) => {
    const { user, isAuthenticated, loading } = useAuth();
    const location = useLocation();

    if (loading) {
        return <div>Loading...</div>;
    }


    if (!isAuthenticated) {
        return <Navigate to="/login" state={{ from: location }} />
    }

    if (requiredRoles && !user.roles.includes(requiredRoles)) {
        return <Navigate to="/unauthorized" replace />
    }

    return <Outlet />;
}

export default ProtectedRoute;
