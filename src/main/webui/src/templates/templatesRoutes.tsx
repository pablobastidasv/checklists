import MainLayout from "./layout/MainLayout";
import HomePage from "./pages/HomePage";

const templatesRoutes = [
  {
    element: <MainLayout />,
    children: [
      {
        path: '',
        element: <HomePage />,
      },
    ],
  }
];

export default templatesRoutes;
