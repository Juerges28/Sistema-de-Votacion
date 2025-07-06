import React from 'react'
import './Login.css'

const Login: React.FC = () => {
  const handleGoogleLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google'
  }

  return (
    <div className="login-container">
      <div className="login-box">
        <h2 className="login-title">Iniciar Sesión</h2>

        <input
          type="text"
          placeholder="Usuario institucional"
          className="login-input dark-input"
        />
        <input
          type="password"
          placeholder="Contraseña"
          className="login-input dark-input"
        />

        <button className="login-button google-login" onClick={handleGoogleLogin}>
          Iniciar sesión con Google
        </button>
      </div>
    </div>
  )
}

export default Login
