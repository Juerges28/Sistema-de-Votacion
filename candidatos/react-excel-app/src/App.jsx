import { useNavigate } from 'react-router-dom';

function App() {
  const navigate = useNavigate();

  function subirArchivo(endpoint) {
    return async (e) => {
      const form = new FormData();
      form.append("file", e.target.files[0]);
      await fetch(`http://localhost:8080/${endpoint}`, {
        method: "POST",
        body: form,
      }).then(r => r.text()).then(alert);
    };
  }

  return (
    <div>
      <div className="header">Gestión de Candidatos</div>
      <div className="container">
        <div className="upload-section">
          <h2>Subir Excel de Partidos</h2>
          <input className="custom-file" type="file" onChange={subirArchivo("subir-partidos")} accept=".xlsx" />
        </div>

        <div className="upload-section">
          <h2>Subir Excel de Candidatos</h2>
          <input className="custom-file" type="file" onChange={subirArchivo("subir-candidatos")} accept=".xlsx" />
        </div>

        <button className="confirm-button" onClick={() => navigate("/lista")}>Confirmar</button>
      </div>
    </div>
  );
}

export default App;
