import ProtectedRoute from "../auth/ProtectedRoute";
import HomePage from "./pages/HomePage";

const templatesRoutes = [
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: '',
        element: <HomePage />,
      },
    ],
  }
];

export default templatesRoutes;
