import { Navigate, Outlet, useLocation } from "react-router";
import { useAuth } from "./AuthContext";

interface Props {
  requiredRoles?: string;
}

const ProtectedRoute = ({ requiredRoles }: Props) => {
  const { user, isAuthenticated } = useAuth();
  const location = useLocation();

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} />
  }

  if (requiredRoles && !user?.roles.includes(requiredRoles)) {
    return <Navigate to="/unauthorized" replace />
  }

  return <Outlet />;
}

export default ProtectedRoute;
