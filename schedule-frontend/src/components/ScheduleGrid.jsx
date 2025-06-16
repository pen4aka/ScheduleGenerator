import React from "react";
import scheduleData from "../assets/mock-data";

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

function mergeLessons(data) {
  const result = [];
  days.forEach((day) => {
    const lessons = hours.map((time) =>
      data.find((l) => l.day === day && l.time === time)
    );

    const merged = [];
    for (let i = 0; i < lessons.length; i++) {
      const current = lessons[i];
      if (!current) {
        merged.push(null);
        continue;
      }

      let span = 1;
      while (
        i + span < lessons.length &&
        lessons[i + span] &&
        lessons[i + span].subject === current.subject &&
        lessons[i + span].teacher === current.teacher &&
        lessons[i + span].room === current.room &&
        lessons[i + span].week === current.week &&
        lessons[i + span].type === current.type &&
        lessons[i + span].group === current.group
      ) {
        span++;
      }

      merged.push({ ...current, colSpan: span });
      for (let j = 1; j < span; j++) merged.push("skip");
      i += span - 1;
    }

    result.push({ day, lessons: merged });
  });

  return result;
}

export default function ScheduleGrid({ semester }) {
  const filteredData = scheduleData.filter(
    (item) => parseInt(item.semester) === semester
  );

  const groups =
    filteredData.length > 0
      ? [...new Set(filteredData.map((item) => item.group))].filter(
          (g) => g !== "all"
        )
      : ["76", "77", "78", "79"];

  return (
    <div className="space-y-12">
      {groups.map((group) => {
        const groupData = filteredData.filter(
          (item) => item.group === group || item.group === "all"
        );
        const mergedData =
          groupData.length > 0 ? mergeLessons(groupData) : mergeLessons([]);

        return (
          <div key={group}>
            <h2 className="text-xl font-bold text-indigo-600 mb-4">
              Група {group}
            </h2>
            <div className="grid grid-cols-[100px_repeat(14,1fr)] border border-gray-300">
              <div className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center">
                Ден
              </div>
              {hours.map((hour, index) => (
                <div
                  key={`hour-${index}`}
                  className="bg-gray-100 border border-gray-300 text-xs flex items-center justify-center"
                >
                  {hour}
                </div>
              ))}

              {days.map((day) => {
                const lessonRow =
                  mergedData.find((row) => row.day === day)?.lessons ||
                  Array(14).fill(null);
                return (
                  <React.Fragment key={day}>
                    <div className="bg-gray-100 border border-gray-300 font-semibold flex items-center justify-center">
                      {day}
                    </div>
                    {lessonRow.map((lesson, idx) => {
                      if (lesson === "skip") return null;
                      if (!lesson)
                        return (
                          <div
                            key={idx}
                            className="border border-gray-300 h-20 bg-white"
                          ></div>
                        );

                      return (
                        <div
                          key={idx}
                          className={`border border-gray-300 h-20 p-1 text-white text-xs relative overflow-hidden ${
                            lesson.type === "л"
                              ? "bg-blue-500"
                              : lesson.type === "у"
                              ? "bg-green-500"
                              : lesson.type === "л.у"
                              ? "bg-yellow-500"
                              : "bg-gray-500"
                          } ${
                            lesson.week === "odd" || lesson.week === "even"
                              ? "diagonal-cell"
                              : ""
                          }`}
                          style={{ gridColumn: `span ${lesson.colSpan}` }}
                        >
                          <div className="absolute inset-0 z-10 p-1 flex flex-col justify-between">
                            <div>
                              <strong>{lesson.subject}</strong>
                              <br />
                              {lesson.teacher}, стая {lesson.room}
                            </div>
                            {lesson.week !== "all" && (
                              <div className="text-[10px] opacity-80 self-end">
                                {lesson.week === "odd" ? "нечетна" : "четна"}
                              </div>
                            )}
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
