import { Outlet } from "react-router"
import { useAuth } from "../../auth/AuthContext";

const StaticLayout = () => {
    const { logout } = useAuth();

    return (
        <>
            <nav>
                <ul>
                    <li><a href="/">Home</a></li>
                    <li><a href="/checklists">Checklists</a></li>
                </ul>
            </nav>

            <button onClick={logout}>Logout</button>

            <Outlet />
        </>
    )
}

export default StaticLayout
