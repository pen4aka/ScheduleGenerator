import { useState } from "react";
import { useNavigate } from "react-router-dom";

// Локални креденшъли за тест
const adminCredentials = {
  admin: "admin123",
  testuser: "pass456",
};

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();

    if (adminCredentials[username] && adminCredentials[username] === password) {
      navigate("/dashboard");
    } else {
      alert("Невалидно потребителско име или парола");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded shadow-md w-full max-w-sm"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Админ Вход</h2>

        <label className="block mb-2 text-sm font-medium">
          Потребителско име
        </label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="w-full p-2 mb-4 border rounded"
          required
        />

        <label className="block mb-2 text-sm font-medium">Парола</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="w-full p-2 mb-6 border rounded"
          required
        />

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
