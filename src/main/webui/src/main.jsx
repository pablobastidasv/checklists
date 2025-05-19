import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter, Routes, Route } from 'react-router'

import App from '@/App.jsx'

import { AuthProvider } from '@auth/AuthContext.jsx'
import Unauthorized from '@auth/pages/Unautorized.jsx'
import LoginPage from '@auth/pages/Login.jsx'
import ProtectedRoute from '@auth/ProtectedRoute.jsx'

import ChecklistsPage from './todos/pages/Checklists.jsx'
import ChecklistsLayout from './todos/templates/ChecklistLayout.jsx'

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    <Route path="login" element={<LoginPage />} />
                    <Route path="unauthorized" element={<Unauthorized />} />

                    <Route element={<ProtectedRoute />}>
                        <Route path="/" element={<App />} />
                    </Route>

                    <Route element={<ChecklistsLayout />}>
                        <Route element={<ProtectedRoute />}>
                            <Route path="checklists" element={<ChecklistsPage />} />
                        </Route>
                    </Route>

                </Routes>
            </AuthProvider>
        </BrowserRouter>
    </StrictMode>,
)
