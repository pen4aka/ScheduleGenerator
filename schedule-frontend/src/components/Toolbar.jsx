import React from "react";

export default function Toolbar({
  onGenerate,
  onExport,
  semester,
  setSemester,
}) {
  return (
    <div className="flex flex-col sm:flex-row justify-between items-center mb-4 bg-white shadow-md p-4 rounded-xl space-y-4 sm:space-y-0">
      <div className="flex flex-col sm:flex-row items-center gap-4">
        <h1 className="text-xl font-bold">Избор на семестър</h1>
        <select
          value={semester}
          onChange={(e) => setSemester(parseInt(e.target.value))}
          className="border border-gray-300 rounded p-2"
        >
          {[...Array(8)].map((_, i) => (
            <option key={i} value={i + 1}>
              Семестър {i + 1}
            </option>
          ))}
        </select>
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
