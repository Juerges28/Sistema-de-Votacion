import React, { useEffect, useState } from 'react'
import LoginButton from './components/LoginButton'
import { useNavigate } from 'react-router-dom'

const App: React.FC = () => {
  const [authenticated, setAuthenticated] = useState<boolean | null>(null)
  const navigate = useNavigate()

  useEffect(() => {
    fetch('http://localhost:8080/api/user', {
      credentials: 'include'
    })
      .then(res => {
        if (res.status === 200) {
          setAuthenticated(true)
          navigate('/dashboard')
        } else {
          setAuthenticated(false)
        }
      })
      .catch(() => setAuthenticated(false))
  }, [])

  if (authenticated === null) return <p>Cargando...</p>

  return (
    <div>
      <h1>Bienvenido al sistema de votación</h1>
      <LoginButton />
    </div>
  )
}

export default App
