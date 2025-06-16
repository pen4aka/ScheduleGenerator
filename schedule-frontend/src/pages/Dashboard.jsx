import { useState, useRef } from "react";
import Toolbar from "../components/Toolbar";
import ScheduleGrid from "../components/ScheduleGrid";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

export default function Dashboard() {
  const [semester, setSemester] = useState(1);
  const exportRef = useRef(null);

  const handleGenerate = () => {
    // In real app: fetch from backend
    alert("Генериране на разписание за семестър " + semester);
  };

  const handleExport = async () => {
    if (!exportRef.current) {
      alert("❌ Няма съдържание за експортиране.");
      return;
    }

    try {
      const canvas = await html2canvas(exportRef.current, { scale: 2 });
      const imgData = canvas.toDataURL("image/png");
      const pdf = new jsPDF({
        orientation: "landscape",
        unit: "px",
        format: [canvas.width, canvas.height],
      });
      pdf.addImage(imgData, "PNG", 0, 0, canvas.width, canvas.height);
      pdf.save(`schedule-semester-${semester}.pdf`);
    } catch (err) {
      console.error("PDF Export Error:", err);
      alert("⚠️ Възникна грешка при експортирането.");
    }
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
          ref={exportRef}
          className="mt-8 overflow-x-auto rounded-lg border border-gray-300 shadow-sm bg-white"
        >
          <ScheduleGrid semester={semester} />
        </div>
      </div>
    </div>
  );
}
