interface LoginFormProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const LoginForm = ({ onSubmit }: LoginFormProps) => {
  return (
    <form onSubmit={onSubmit}>
      <div>
        <span>Username</span>
        <input type="text" placeholder="Enter your username" name="username" />
      </div>
      <div>
        <span>Password</span>
        <input type="password" placeholder="Enter your password" name="password" />
      </div>
      <div>
        <button>Login</button>
      </div>
    </form>
  );
};

export default LoginForm;
