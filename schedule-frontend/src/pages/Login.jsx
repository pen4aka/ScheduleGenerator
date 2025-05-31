import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [credentials, setCredentials] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("/credentials.json")
      .then((res) => res.json())
      .then((data) => setCredentials(data))
      .catch((err) =>
        console.error("Грешка при зареждане на credentials:", err)
      );
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    const match = credentials.find(
      (user) => user.username === username && user.password === password
    );

    if (!match) {
      alert("Невалидно потребителско име или парола");
      return;
    }

    navigate("/admin");
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded shadow-md w-full max-w-md space-y-4"
      >
        <h2 className="text-2xl font-bold text-center">Админ Вход</h2>

        <div>
          <label className="block mb-1 font-medium">Потребителско име</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="w-full border p-2 rounded"
            required
          />
        </div>

        <div>
          <label className="block mb-1 font-medium">Парола</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full border p-2 rounded"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600"
        >
          Вход
        </button>
      </form>
    </div>
  );
}
