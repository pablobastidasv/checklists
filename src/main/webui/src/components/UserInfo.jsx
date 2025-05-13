import { useState, useEffect } from 'react'
import { getUserInfo } from '../api/users'

const UserInfo = () => {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(null)
    const [isError, setIsError] = useState(false)

    useEffect(() => {
        const fetchUserInfo = async () => {
            setLoading(true)
            try {
                const user = await getUserInfo()
                setUser(user)
            } catch (error) {
                setError(error)
                setIsError(true)
            }
            setLoading(false)
        }

        fetchUserInfo()
    }, [])


    return (
        <>
            {loading && <div>Loading...</div>}
            {isError && <div>Error: {error}</div>}
            {user && <div>
                <img src={user.avatar} alt="Avatar" />
                <br />
                Name: {user.name}
                <br />
                Email: {user.email}
                <br />
                Id: {user.id}
            </div>}
        </>
    )
}

export default UserInfo
