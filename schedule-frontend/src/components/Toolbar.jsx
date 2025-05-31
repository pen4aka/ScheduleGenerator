import React from "react";

export default function Toolbar({
  onGenerate,
  onExport,
  selectedSemester,
  onSemesterChange,
}) {
  return (
    <div className="flex flex-wrap justify-between items-center mb-4 bg-white shadow-md p-4 rounded-xl gap-4">
      <div className="flex flex-col sm:flex-row sm:items-center sm:gap-4">
        <h1 className="text-xl font-bold">Генератор на разписание</h1>

        <div className="mt-2 sm:mt-0">
          <label className="block text-sm font-medium mb-1">Семестър:</label>
          <select
            value={selectedSemester}
            onChange={(e) => onSemesterChange(Number(e.target.value))}
            className="border rounded px-3 py-1"
          >
            {Array.from({ length: 8 }, (_, i) => (
              <option key={i + 1} value={i + 1}>
                Семестър {i + 1}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div className="space-x-2">
        <button
          onClick={onGenerate}
          className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition"
        >
          Генерирай разписание
        </button>
        <button
          onClick={onExport}
          className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-lg transition"
        >
          Експортиране
        </button>
      </div>
    </div>
  );
}
