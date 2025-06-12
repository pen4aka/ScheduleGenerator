import { useState } from "react";
import Toolbar from "../components/Toolbar";
import ScheduleGrid from "../components/ScheduleGrid";
import scheduleData from "../assets/mock-data";

export default function Dashboard() {
  const [semester, setSemester] = useState(1);

  const handleGenerate = () => {
    alert("Генериране на разписание за семестър " + semester);
  };

  const handleExport = () => {
    const blob = new Blob([JSON.stringify(scheduleData, null, 2)], {
      type: "application/json",
    });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "schedule.json";
    link.click();
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-blue-50 to-indigo-100 p-6">
      <div className="max-w-7xl mx-auto bg-white rounded-2xl shadow-lg p-6 transition-all duration-300">
        <h1 className="text-3xl font-extrabold text-center text-indigo-700 mb-8">
          📅 Седмично разписание
        </h1>

        <Toolbar
          onGenerate={handleGenerate}
          onExport={handleExport}
          semester={semester}
          setSemester={setSemester}
        />

        <div className="mt-8 overflow-x-auto rounded-lg border border-gray-300 shadow-sm">
          <ScheduleGrid semester={semester} />
        </div>
      </div>
    </div>
  );
}
