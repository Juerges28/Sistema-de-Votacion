import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function ListaCandidatos() {
  const [candidatos, setCandidatos] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("/tmp/candidatos.json")
      .then(r => r.json())
      .then(setCandidatos);
  }, []);

  function limpiar() {
    fetch("/tmp/candidatos.json", { method: "DELETE" });
    setTimeout(() => navigate("/"), 500);
  }

  return (
    <div className="container">
      <h2>Lista de Candidatos</h2>
      <table>
        <thead>
          <tr>
            <th>Nombres</th>
            <th>Apellidos</th>
            <th>Foto</th>
            <th>Partido</th>
            <th>Icono</th>
          </tr>
        </thead>
        <tbody>
          {candidatos.map((c, i) => (
            <tr key={i}>
              <td>{c.nombres}</td>
              <td>{c.apellidos}</td>
              <td><img src={`/${c.foto}`} width={100} /></td>
              <td>{c.partido}</td>
              <td><img src={`/${c.icono}`} width={50} /></td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="confirm-button" onClick={limpiar}>Confirmar</button>
    </div>
  );
}

export default ListaCandidatos;
