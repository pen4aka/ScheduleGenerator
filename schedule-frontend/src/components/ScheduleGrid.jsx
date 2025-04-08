import React from "react";
import scheduleData from "../assets/mock-data";

export default function ScheduleGrid() {
  const days = ["Пон", "Вт", "Ср", "Чт", "Пт"];
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

  return (
    <div className="grid grid-cols-[100px_repeat(5,1fr)] border border-gray-300">
      {/* Top Row (Days) */}
      <div className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center">
        Час
      </div>
      {days.map((day) => (
        <div
          key={day}
          className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center"
        >
          {day}
        </div>
      ))}

      {/* Rows (Times + cells) */}
      {hours.map((hour, rowIndex) => (
        <React.Fragment key={`row-${rowIndex}`}>
          <div className="border border-gray-300 text-sm flex items-center justify-center">
            {hour}
          </div>
          {days.map((day) => {
            const lesson = scheduleData.find(
              (item) => item.day === day && item.time === hour
            );

            return (
              <div
                key={`${day}-${rowIndex}`}
                className={`border border-gray-300 h-16 p-1 relative ${
                  lesson?.type === "л"
                    ? "bg-blue-100"
                    : lesson?.type === "у"
                    ? "bg-green-100"
                    : lesson?.type === "л.у"
                    ? "bg-yellow-100"
                    : "bg-gray-100"
                }`}
              >
                {lesson && (
                  <div
                    className={`text-white text-xs rounded p-1 h-full w-full ${
                      lesson?.type === "л"
                        ? "bg-blue-500"
                        : lesson?.type === "у"
                        ? "bg-green-500"
                        : lesson?.type === "л.у"
                        ? "bg-yellow-500"
                        : "bg-gray-500"
                    }`}
                  >
                    <strong>{lesson.subject}</strong> <br />
                    стая {lesson.room} <br />
                    <em>
                      {lesson.type === "л"
                        ? "Лекция"
                        : lesson.type === "у"
                        ? "Упражнение"
                        : lesson.type === "л.у"
                        ? "Лабораторно Упражнение"
                        : "Друго"}
                    </em>
                  </div>
                )}
              </div>
            );
          })}
        </React.Fragment>
      ))}
    </div>
  );
}
