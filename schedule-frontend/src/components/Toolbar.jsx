import React, { useState } from "react";
import AdminUpload from "../pages/AdminUpload"; // увери се, че пътят е коректен

export default function Toolbar({
  onGenerate,
  onExport,
  semester,
  setSemester,
}) {
  const [showUpload, setShowUpload] = useState(false);
  const role = localStorage.getItem("role");

  return (
    <>
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

        <div className="space-x-2 flex flex-wrap justify-center">
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

          {role === "admin" && (
            <button
              onClick={() => setShowUpload(true)}
              className="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-lg transition"
            >
              Качване на данни
            </button>
          )}
        </div>
      </div>

      {showUpload && (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center">
          <div className="relative bg-white rounded-xl shadow-2xl p-6 max-w-3xl w-full max-h-[90vh] overflow-y-auto">
            <button
              className="absolute top-3 right-4 text-xl text-gray-500 hover:text-red-500"
              onClick={() => setShowUpload(false)}
            >
              ✖
            </button>
            <AdminUpload />
          </div>
        </div>
      )}
    </>
  );
}
