import { useState } from "react";
import { useNavigate } from "react-router-dom";
import credentials from "../../public/credentials.json"; // mock файл с креденшъли

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    const user = credentials.find(
      (u) => u.username === username && u.password === password
    );

    if (user) {
      const role = user.role;
      if (role === "admin") navigate("/admin");
      else if (role === "user") navigate("/dashboard");
      else alert("Неизвестна роля");
    } else {
      alert("Невалидни данни за вход.");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleLogin}
        className="bg-white p-6 rounded shadow-md w-80 space-y-4"
      >
        <h2 className="text-2xl font-bold text-center">Вход</h2>
        <input
          type="text"
          placeholder="Потребителско име"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full border p-2 rounded"
          required
        />
        <input
          type="password"
          placeholder="Парола"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full border p-2 rounded"
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
        >
          Вход
        </button>
      </form>
    </div>
  );
}
