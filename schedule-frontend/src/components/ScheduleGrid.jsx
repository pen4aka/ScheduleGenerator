import React from "react";

const days = ["ПОНЕДЕЛНИК", "ВТОРНИК", "СРЯДА", "ЧЕТВЪРТЪК", "ПЕТЪК"];
const apiDays = {
  MONDAY: "ПОНЕДЕЛНИК",
  TUESDAY: "ВТОРНИК",
  WEDNESDAY: "СРЯДА",
  THURSDAY: "ЧЕТВЪРТЪК",
  FRIDAY: "ПЕТЪК",
};

const hours = [
  "7:30 - 8:15",
  "8:30 - 9:15",
  "9:30 - 10:15",
  "10:30 - 11:15",
  "11:30 - 12:15",
  "12:30 - 13:15",
  "13:45 - 14:30",
  "14:45 - 15:30",
  "15:45 - 16:30",
  "16:45 - 17:30",
  "17:45 - 18:30",
  "18:45 - 19:30",
  "19:45 - 20:30",
  "20:45 - 21:30",
];

function convertStartTimeToSlotIndex(startTime) {
  const timeMap = {
    "07:30:00": 0,
    "08:00:00": 0,
    "08:30:00": 1,
    "09:30:00": 2,
    "10:30:00": 3,
    "11:30:00": 4,
    "12:30:00": 5,
    "13:00:00": 6,
    "13:45:00": 6,
    "14:45:00": 7,
    "15:45:00": 8,
    "16:45:00": 9,
    "17:00:00": 10,
    "17:45:00": 10,
    "18:45:00": 11,
    "19:45:00": 12,
    "20:45:00": 13,
  };
  return timeMap[startTime] ?? 0;
}

export default function ScheduleGrid({ semester, scheduleData }) {
  const filteredData = scheduleData.filter(
    (item) => parseInt(item.semester) === semester
  );

  const groups =
    filteredData.length > 0
      ? [...new Set(filteredData.map((item) => item.group))].sort()
      : ["76", "77", "78", "79"];

  return (
    <div className="space-y-12">
      <div className="text-xs italic text-gray-700 ml-1">
        Легенда: <span className="text-blue-600">Лекции</span>,{" "}
        <span className="text-green-600">Семинарни упражнения</span>,{" "}
        <span className="text-yellow-600">Лабораторни упражнения</span>,{" "}
        <span className="text-gray-700">Друго</span>
      </div>

      {groups.map((group) => {
        const groupData = filteredData.filter((item) => item.group === group);
        return (
          <div key={group}>
            <h2 className="text-xl font-bold text-indigo-600 mb-4">
              Група {group}
            </h2>
            <div className="grid grid-cols-[100px_repeat(14,1fr)] border border-gray-300">
              <div className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center text-sm">
                Ден
              </div>
              {hours.map((hour, i) => (
                <div
                  key={`hour-${i}`}
                  className="bg-gray-100 border border-gray-300 text-[10px] flex items-center justify-center px-1 text-center"
                >
                  {hour}
                </div>
              ))}

              {days.map((dayName) => {
                const dayEntries = groupData.filter(
                  (e) => apiDays[e.day] === dayName
                );
                const cells = Array(14).fill(null);

                dayEntries.forEach((entry) => {
                  const start = convertStartTimeToSlotIndex(entry.time);
                  const isLecture = entry.type === "л";
                  const duration = isLecture ? 2 : 1;
                  cells[start] = { ...entry, colSpan: duration };
                  for (let i = 1; i < duration; i++) {
                    cells[start + i] = "skip";
                  }
                });

                return (
                  <React.Fragment key={dayName}>
                    <div className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center text-sm">
                      {dayName}
                    </div>
                    {cells.map((cell, i) => {
                      if (cell === "skip") return null;
                      if (!cell)
                        return (
                          <div
                            key={i}
                            className="border border-gray-300 min-h-[70px] bg-white"
                          ></div>
                        );
                      return (
                        <div
                          key={i}
                          className={`border border-gray-300 p-1 text-white text-xs overflow-hidden whitespace-pre-wrap break-words leading-tight flex items-center justify-center text-center ${
                            cell.type === "л"
                              ? "bg-blue-500"
                              : cell.type === "у"
                              ? "bg-green-500"
                              : cell.type === "л.у"
                              ? "bg-yellow-500"
                              : "bg-gray-500"
                          }`}
                          style={{
                            gridColumn: `span ${cell.colSpan}`,
                            minHeight: "70px",
                          }}
                        >
                          <div className="w-full">
                            <div className="font-semibold">{cell.subject}</div>
                            <div className="text-[11px]">Стая: {cell.room}</div>
                          </div>
                        </div>
                      );
                    })}
                  </React.Fragment>
                );
              })}
            </div>
          </div>
        );
      })}
    </div>
  );
}
