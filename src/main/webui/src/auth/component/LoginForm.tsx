import Button from "../../atoms/Button";

interface LoginFormProps {
  onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
}

const LoginForm = ({ onSubmit }: LoginFormProps) => {
  return (
    <form onSubmit={onSubmit}>
      <fieldset className="fieldset bg-base-200 border-base-300 rounded-box w-xs border p-4">

        <label className="label">Email</label>
        <input type="email" className="input" placeholder="Email" name="username" autoFocus />

        <label className="label">Password</label>
        <input type="password" className="input" placeholder="Password" name="password" />

        <Button>Login</Button>
      </fieldset>
    </form>
  );
};

export default LoginForm;
