import { useAuth } from "../AuthContext"

const LoginForm = () => {
    const { login } = useAuth()

    const handleSubmit = (domainEvent) => {
        domainEvent.preventDefault()
        const formData = new FormData(domainEvent.target)
        const user = {
            email: formData.get('username'),
            password: formData.get('password'),
        }
        login(user)
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" name="username" />
                </label>
                <br />
                <label>
                    Password:
                    <input type="password" name="password" />
                </label>
                <br />
                <button type="submit">Login</button>
            </form>
        </>
    )
}

export default LoginForm
