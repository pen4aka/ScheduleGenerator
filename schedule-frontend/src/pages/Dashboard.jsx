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
    <div className="p-4">
      <Toolbar
        onGenerate={handleGenerate}
        onExport={handleExport}
        semester={semester}
        setSemester={setSemester}
      />
      <ScheduleGrid semester={semester} />
    </div>
  );
}
