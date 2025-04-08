import Toolbar from "./components/Toolbar";
import ScheduleGrid from "./components/ScheduleGrid";

function App() {
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
