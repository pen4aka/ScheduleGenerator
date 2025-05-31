import { useState } from "react";

export default function AdminPanel() {
  const [semester, setSemester] = useState(1);
  const [rooms, setRooms] = useState([
    { name: "", capacity: "", projector: false, computers: false },
  ]);
  const [teachers, setTeachers] = useState([""]);
  const [subjects, setSubjects] = useState([""]);
  const [groups, setGroups] = useState([{ name: "", size: "" }]);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Изпратени данни:", {
      semester,
      rooms,
      teachers,
      subjects,
      groups,
    });
  };

  const addRoom = () => {
    setRooms([
      ...rooms,
      { name: "", capacity: "", projector: false, computers: false },
    ]);
  };

  const addTeacher = () => {
    setTeachers([...teachers, ""]);
  };

  const addSubject = () => {
    setSubjects([...subjects, ""]);
  };

  const addGroup = () => {
    setGroups([...groups, { name: "", size: "" }]);
  };

  return (
    <div className="p-6 max-w-5xl mx-auto">
      <h1 className="text-2xl font-bold mb-6">
        Админ панел: Въвеждане на данни
      </h1>

      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label className="font-medium block mb-1">Семестър</label>
          <select
            value={semester}
            onChange={(e) => setSemester(parseInt(e.target.value))}
            className="w-full border p-2 rounded"
          >
            {[...Array(8)].map((_, i) => (
              <option key={i} value={i + 1}>
                Семестър {i + 1}
              </option>
            ))}
          </select>
        </div>

        {/* Стаи */}
        <div>
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-semibold">Стаи</h2>
            <button type="button" onClick={addRoom} className="text-blue-600">
              + Добави стая
            </button>
          </div>
          {rooms.map((room, i) => (
            <div
              key={i}
              className="grid grid-cols-2 gap-4 border p-4 mt-2 rounded"
            >
              <input
                placeholder="Име на стая"
                value={room.name}
                onChange={(e) => {
                  const newRooms = [...rooms];
                  newRooms[i].name = e.target.value;
                  setRooms(newRooms);
                }}
                className="border p-2 rounded"
                required
              />
              <input
                placeholder="Капацитет"
                type="number"
                value={room.capacity}
                onChange={(e) => {
                  const newRooms = [...rooms];
                  newRooms[i].capacity = e.target.value;
                  setRooms(newRooms);
                }}
                className="border p-2 rounded"
                required
              />
              <label className="col-span-1 flex items-center gap-2">
                <input
                  type="checkbox"
                  checked={room.projector}
                  onChange={(e) => {
                    const newRooms = [...rooms];
                    newRooms[i].projector = e.target.checked;
                    setRooms(newRooms);
                  }}
                />
                Има проектор
              </label>
              <label className="col-span-1 flex items-center gap-2">
                <input
                  type="checkbox"
                  checked={room.computers}
                  onChange={(e) => {
                    const newRooms = [...rooms];
                    newRooms[i].computers = e.target.checked;
                    setRooms(newRooms);
                  }}
                />
                Има компютри
              </label>
            </div>
          ))}
        </div>

        {/* Преподаватели */}
        <div>
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-semibold">Преподаватели</h2>
            <button
              type="button"
              onClick={addTeacher}
              className="text-blue-600"
            >
              + Добави преподавател
            </button>
          </div>
          {teachers.map((name, i) => (
            <input
              key={i}
              value={name}
              placeholder={`Преподавател ${i + 1}`}
              onChange={(e) => {
                const updated = [...teachers];
                updated[i] = e.target.value;
                setTeachers(updated);
              }}
              className="w-full border p-2 rounded mt-2"
              required
            />
          ))}
        </div>

        {/* Предмети */}
        <div>
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-semibold">Предмети</h2>
            <button
              type="button"
              onClick={addSubject}
              className="text-blue-600"
            >
              + Добави предмет
            </button>
          </div>
          {subjects.map((name, i) => (
            <input
              key={i}
              value={name}
              placeholder={`Предмет ${i + 1}`}
              onChange={(e) => {
                const updated = [...subjects];
                updated[i] = e.target.value;
                setSubjects(updated);
              }}
              className="w-full border p-2 rounded mt-2"
              required
            />
          ))}
        </div>

        {/* Групи */}
        <div>
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-semibold">Групи</h2>
            <button type="button" onClick={addGroup} className="text-blue-600">
              + Добави група
            </button>
          </div>
          <div className="grid grid-cols-2 gap-4">
            {groups.map((group, i) => (
              <div key={i} className="border p-3 rounded">
                <input
                  placeholder="Име на група"
                  value={group.name}
                  onChange={(e) => {
                    const updated = [...groups];
                    updated[i].name = e.target.value;
                    setGroups(updated);
                  }}
                  className="w-full border p-2 rounded mb-2"
                  required
                />
                <input
                  placeholder="Капацитет"
                  type="number"
                  value={group.size}
                  onChange={(e) => {
                    const updated = [...groups];
                    updated[i].size = e.target.value;
                    setGroups(updated);
                  }}
                  className="w-full border p-2 rounded"
                  required
                />
              </div>
            ))}
          </div>
        </div>

        <button
          type="submit"
          className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded"
        >
          Запази данните
        </button>
      </form>
    </div>
  );
}
