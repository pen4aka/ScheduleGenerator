import { useState } from "react";

export default function AdminPanel() {
  const [semester, setSemester] = useState(1);
  const [rooms, setRooms] = useState([
    { name: "", capacity: "", projector: false, computers: false },
  ]);
  const [teachers, setTeachers] = useState([""]);
  const [subjects, setSubjects] = useState([{ name: "", type: "л" }]);
  const [groups, setGroups] = useState([{ name: "", capacity: "" }]);

  const addItem = (setter, defaultValue) =>
    setter((prev) => [...prev, defaultValue]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      semester,
      rooms,
      teachers,
      subjects,
      groups,
    };
    console.log("Данни за запис:", data);
  };

  return (
    <div className="p-4 space-y-6">
      <h2 className="text-2xl font-semibold">Добавяне на информация</h2>
      <form onSubmit={handleSubmit} className="space-y-4 max-w-xl">
        <div>
          <label className="block font-semibold">Семестър:</label>
          <select
            value={semester}
            onChange={(e) => setSemester(Number(e.target.value))}
            className="w-full border border-gray-300 rounded p-2"
          >
            {[...Array(8)].map((_, i) => (
              <option key={i} value={i + 1}>{`Семестър ${i + 1}`}</option>
            ))}
          </select>
        </div>

        {/* Стаи */}
        <div>
          <div className="flex justify-between items-center">
            <label className="block font-semibold">Стаи</label>
            <button
              type="button"
              onClick={() =>
                addItem(setRooms, {
                  name: "",
                  capacity: "",
                  projector: false,
                  computers: false,
                })
              }
              className="text-blue-600"
            >
              + Добави стая
            </button>
          </div>
          {rooms.map((room, index) => (
            <div key={index} className="border p-3 rounded space-y-2 mt-2">
              <input
                type="text"
                placeholder="Име"
                value={room.name}
                onChange={(e) => {
                  const newRooms = [...rooms];
                  newRooms[index].name = e.target.value;
                  setRooms(newRooms);
                }}
                className="w-full border p-2 rounded"
              />
              <input
                type="number"
                placeholder="Капацитет"
                value={room.capacity}
                onChange={(e) => {
                  const newRooms = [...rooms];
                  newRooms[index].capacity = e.target.value;
                  setRooms(newRooms);
                }}
                className="w-full border p-2 rounded"
              />
              <label className="flex items-center gap-2">
                <input
                  type="checkbox"
                  checked={room.projector}
                  onChange={(e) => {
                    const newRooms = [...rooms];
                    newRooms[index].projector = e.target.checked;
                    setRooms(newRooms);
                  }}
                />{" "}
                Проектор
              </label>
              <label className="flex items-center gap-2">
                <input
                  type="checkbox"
                  checked={room.computers}
                  onChange={(e) => {
                    const newRooms = [...rooms];
                    newRooms[index].computers = e.target.checked;
                    setRooms(newRooms);
                  }}
                />{" "}
                Компютри
              </label>
            </div>
          ))}
        </div>

        {/* Преподаватели */}
        <div>
          <div className="flex justify-between items-center">
            <label className="block font-semibold">Преподаватели</label>
            <button
              type="button"
              onClick={() => addItem(setTeachers, "")}
              className="text-blue-600"
            >
              + Добави преподавател
            </button>
          </div>
          {teachers.map((name, index) => (
            <input
              key={index}
              type="text"
              value={name}
              placeholder={`Преподавател ${index + 1}`}
              onChange={(e) => {
                const updated = [...teachers];
                updated[index] = e.target.value;
                setTeachers(updated);
              }}
              className="w-full border p-2 rounded mt-2"
            />
          ))}
        </div>

        {/* Предмети с тип */}
        <div>
          <div className="flex justify-between items-center">
            <label className="block font-semibold">Предмети</label>
            <button
              type="button"
              onClick={() => addItem(setSubjects, { name: "", type: "л" })}
              className="text-blue-600"
            >
              + Добави предмет
            </button>
          </div>
          {subjects.map((subject, index) => (
            <div key={index} className="mt-2">
              <input
                type="text"
                value={subject.name}
                placeholder={`Предмет ${index + 1}`}
                onChange={(e) => {
                  const updated = [...subjects];
                  updated[index].name = e.target.value;
                  setSubjects(updated);
                }}
                className="w-full border p-2 rounded mb-1"
              />
              <select
                value={subject.type}
                onChange={(e) => {
                  const updated = [...subjects];
                  updated[index].type = e.target.value;
                  setSubjects(updated);
                }}
                className="w-full border p-2 rounded"
              >
                <option value="л">Лекция</option>
                <option value="л.у">Лабораторно упражнение</option>
                <option value="с.у">Семинарно упражнение</option>
              </select>
            </div>
          ))}
        </div>

        {/* Групи */}
        <div>
          <div className="flex justify-between items-center">
            <label className="block font-semibold">Групи</label>
            <button
              type="button"
              onClick={() => addItem(setGroups, { name: "", capacity: "" })}
              className="text-blue-600"
            >
              + Добави група
            </button>
          </div>
          {groups.map((group, index) => (
            <div key={index} className="border p-3 rounded space-y-2 mt-2">
              <input
                type="text"
                placeholder="Име на група"
                value={group.name}
                onChange={(e) => {
                  const updated = [...groups];
                  updated[index].name = e.target.value;
                  setGroups(updated);
                }}
                className="w-full border p-2 rounded"
              />
              <input
                type="number"
                placeholder="Капацитет"
                value={group.capacity}
                onChange={(e) => {
                  const updated = [...groups];
                  updated[index].capacity = e.target.value;
                  setGroups(updated);
                }}
                className="w-full border p-2 rounded"
              />
            </div>
          ))}
        </div>

        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Запази информация
        </button>
      </form>
    </div>
  );
}
