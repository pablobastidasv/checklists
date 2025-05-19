import { useAuth } from "@auth/AuthContext";
import { Outlet } from "react-router";

const ChecklistsLayout = () => {
    const { logout } = useAuth();

    return <>
        <button onClick={logout}>Logout</button>

        <hr />

        <Outlet />

        <hr />
    </>;
}

export default ChecklistsLayout;
