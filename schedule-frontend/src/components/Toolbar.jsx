//for generating schedules or switching weeks (odd/even)
import React from "react";

export default function Toolbar({ onGenerate, onExport }) {
  return (
    <div className="flex justify-between items-center mb-4 bg-white shadow-md p-4 rounded-xl">
      <h1 className="text-xl font-bold">Генератор на разписание</h1>
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
        <div className="flex gap-4 text-sm mt-2">
          <div className="flex items-center gap-1">
            <div className="w-4 h-4 bg-blue-100 border border-gray-400 rounded" />
            Лекция
          </div>
          <div className="flex items-center gap-1">
            <div className="w-4 h-4 bg-green-100 border border-gray-400 rounded" />
            Упражнение
          </div>
          <div className="flex items-center gap-1">
            <div className="w-4 h-4 bg-yellow-100 border border-gray-400 rounded" />
            Лабораторно Упражнение
          </div>
          <div className="flex items-center gap-1">
            <div className="w-4 h-4 bg-gray-100 border border-gray-400 rounded" />
            Друго
          </div>
        </div>
      </div>
    </div>
  );
}
