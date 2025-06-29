import './App.css'
import { Navigate, useNavigate } from 'react-router'
import { useAuth } from './auth/AuthContext'
import Button from './atoms/Button';

function App() {
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  if (isAuthenticated) {
    return <Navigate to="/templates" replace />;
  }

  return (
    <>
      {isAuthenticated ? (
        <Button onClick={logout}>Logout</Button>
      ) : (
        <>
          <button className="btn" onClick={() => navigate('/login')}>Login</button>
        </>
      )}
    </>
  )
}

export default App
