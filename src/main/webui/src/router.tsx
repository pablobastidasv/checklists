import { createBrowserRouter } from "react-router"

import App from './App.tsx'
import authRoutes from "./auth/auth_routes.tsx"
import templateRoutes from "./templates/templatesRoutes.tsx"

const router = createBrowserRouter([
  ...authRoutes,
  {
    path: '/templates',
    children: templateRoutes,
  },
  {
    path: '/',
    element: <App />,
    errorElement: <App />,
  },
])

export default router

