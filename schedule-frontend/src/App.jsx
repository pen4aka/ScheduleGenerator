import Toolbar from "./components/Toolbar";
import ScheduleGrid from "./components/ScheduleGrid";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import SchedulePage from "./pages/SchedulePage"; // по-късно ще я направим

function App() {
  <Router>
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/schedule" element={<SchedulePage />} />
    </Routes>
  </Router>;
  const handleGenerate = () => {
    alert("Генериране на разписание...");
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
      <Toolbar onGenerate={handleGenerate} onExport={handleExport} />
      <ScheduleGrid />
    </div>
  );
}

export default App;
