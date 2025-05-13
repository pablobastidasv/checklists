import { useState, useEffect } from 'react'
import { getChecklists } from '../api/checklists'

const TodoList = () => {
    const [todos, setTodos] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(null)
    const [isError, setIsError] = useState(false)

    useEffect(() => {
        const fetchChecklists = async () => {
            setLoading(true)
            try {
                const data = await getChecklists()
                setTodos(data)
            } catch (error) {
                setError(error)
                setIsError(true)
            }
            setLoading(false)
        }

        fetchChecklists()
    }, [])

    return (
        <>
            {loading && <div>Loading...</div>}
            {isError && <div>Error: {error}</div>}
            {todos && <div>
                <ul>
                    {todos.map((todo) => (
                        <li key={todo.description}>{todo.description}</li>
                    ))}
                </ul>
            </div>}
        </>
    )
}

export default TodoList
