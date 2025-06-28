import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Toolbar from "../components/Toolbar";
import ScheduleGrid from "../components/ScheduleGrid";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

export default function Dashboard() {
  const [semester, setSemester] = useState(1);
  const [season, setSeason] = useState("winter");
  const [scheduleData, setScheduleData] = useState([]);

  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      navigate("/");
    }
  }, [token, navigate]);

  const handleGenerate = async () => {
    try {
      const seasonParam = season === "winter" ? "ЗИМЕН" : "ЛЕТЕН";

      const generate = await fetch(
        `http://localhost:8080/api/schedule/generate/by-season?season=${seasonParam}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!generate.ok) throw new Error("Грешка при генериране");

      const response = await fetch(
        `http://localhost:8080/api/schedule/view?semesterId=${semester}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const data = await response.json();

      const formatted = [];

      data.forEach((entry) => {
        entry.groupNames.forEach((group) => {
          formatted.push({
            day: entry.day,
            time: entry.startTime,
            subject: entry.subjectName,
            teacher: entry.teacherName,
            room: entry.roomName,
            type:
              entry.type === "ЛЕКЦИИ"
                ? "л"
                : entry.type === "СЕМИНАРНИ"
                ? "у"
                : entry.type === "ЛАБОРАТОРНИ"
                ? "л.у"
                : "друго",
            week: "all",
            group,
            semester: semester,
          });
        });
      });

      setScheduleData(formatted);
    } catch (err) {
      alert("⚠️ Възникна грешка при генериране или извличане.");
      console.error(err);
    }
  };

  const handleExport = async () => {
    const element = document.getElementById("export-pdf");
    if (!element) {
      alert("❌ Не е намерен елемент за експортиране.");
      return;
    }

    try {
      const canvas = await html2canvas(element, { scale: 2 });
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
          season={season}
          setSeason={setSeason}
        />

        <div
          id="export-pdf"
          className="mt-8 overflow-x-auto rounded-lg border border-gray-300 shadow-sm bg-white"
        >
          <ScheduleGrid semester={semester} scheduleData={scheduleData} />
        </div>
      </div>
    </div>
  );
}
