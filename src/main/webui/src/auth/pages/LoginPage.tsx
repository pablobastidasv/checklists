import { useNavigate } from "react-router";
import { useAuth } from "../AuthContext";
import LoginForm from "../component/LoginForm";
import { useState } from "react";
import Alert from "../../atoms/Alert";

const LoginPage = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const username = formData.get('username')?.toString();
    const password = formData.get('password')?.toString();

    if (!username || !password) {
      return;
    }

    try {
      await login(username, password);
      navigate("/");
    } catch (error: (Error | unknown)) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        console.error(error);
        setError("Unknown error");
      }
    }
  }

  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <div>
        <h1>Login Page</h1>
      </div>

      {error && <Alert type="error">{error}</Alert>}

      <LoginForm onSubmit={onSubmit} />
    </div>

  );
};

export default LoginPage;
