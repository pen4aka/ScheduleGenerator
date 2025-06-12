import { useState } from "react";
import Toolbar from "../components/Toolbar";
import ScheduleGrid from "../components/ScheduleGrid";
import html2pdf from "html2pdf.js";

export default function Dashboard() {
  const [semester, setSemester] = useState(1);

  const handleGenerate = () => {
    alert("Генериране на разписание за семестър " + semester);
  };

  const handleExport = () => {
    const element = document.getElementById("export-pdf");

    if (!element) {
      alert("❌ Не е намерен елемент за експортиране.");
      return;
    }

    const opt = {
      margin: 0.3,
      filename: `schedule-semester-${semester}.pdf`,
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: "in", format: "a2", orientation: "landscape" },
    };

    html2pdf().set(opt).from(element).save();
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-blue-50 to-indigo-100 p-6">
      <div className="max-w-7xl mx-auto bg-white rounded-2xl shadow-lg p-6 transition-all duration-300">
        <h1 className="text-3xl font-extrabold text-center text-indigo-700 mb-8">
          🗓️ Седмично разписание
        </h1>

        <Toolbar
          onGenerate={handleGenerate}
          onExport={handleExport}
          semester={semester}
          setSemester={setSemester}
        />

        <div
          id="export-pdf"
          className="mt-8 overflow-x-auto rounded-lg border border-gray-300 shadow-sm bg-white"
        >
          <ScheduleGrid semester={semester} />
        </div>
      </div>
    </div>
  );
}
