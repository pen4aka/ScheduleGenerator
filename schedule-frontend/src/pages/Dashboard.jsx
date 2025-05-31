import { useState } from "react";

export default function Dashboard() {
  const [roomName, setRoomName] = useState("");
  const [capacity, setCapacity] = useState("");
  const [hasProjector, setHasProjector] = useState(false);
  const [hasComputers, setHasComputers] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();

    const roomData = {
      name: roomName,
      capacity: parseInt(capacity),
      projector: hasProjector ? "да" : "не",
      computers: hasComputers ? "да" : "не",
    };

    console.log("Стая за изпращане:", roomData);

    setRoomName("");
    setCapacity("");
    setHasProjector(false);
    setHasComputers(false);
  };

  return (
    <div className="p-8 max-w-xl mx-auto">
      <h2 className="text-2xl font-bold mb-6">Добавяне на стая</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block mb-1 font-medium">Име на стаята</label>
          <input
            type="text"
            value={roomName}
            onChange={(e) => setRoomName(e.target.value)}
            className="w-full border p-2 rounded"
            required
          />
        </div>

        <div>
          <label className="block mb-1 font-medium">Капацитет</label>
          <input
            type="number"
            value={capacity}
            onChange={(e) => setCapacity(e.target.value)}
            className="w-full border p-2 rounded"
            required
          />
        </div>

        <div className="flex items-center space-x-4">
          <label className="flex items-center">
            <input
              type="checkbox"
              checked={hasProjector}
              onChange={(e) => setHasProjector(e.target.checked)}
              className="mr-2"
            />
            Има проектор
          </label>
          <label className="flex items-center">
            <input
              type="checkbox"
              checked={hasComputers}
              onChange={(e) => setHasComputers(e.target.checked)}
              className="mr-2"
            />
            Има компютри
          </label>
        </div>

        <button
          type="submit"
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Запази стая
        </button>
      </form>
    </div>
  );
}
