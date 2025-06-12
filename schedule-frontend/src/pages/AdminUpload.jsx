import { useState, useRef } from "react";
import * as XLSX from "xlsx";
import { UploadCloud, CheckCircle, XCircle } from "lucide-react";

export default function AdminUpload() {
  const [excelData, setExcelData] = useState(null);
  const [fileName, setFileName] = useState("");
  const [message, setMessage] = useState(null);
  const fileInputRef = useRef(null);

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (evt) => {
      const data = new Uint8Array(evt.target.result);
      const workbook = XLSX.read(data, { type: "array" });
      const sheetName = workbook.SheetNames[0];
      const sheet = workbook.Sheets[sheetName];
      const parsedData = XLSX.utils.sheet_to_json(sheet, { defval: "" });
      setExcelData(parsedData);
      setFileName(file.name);
      setMessage(null);
    };
    reader.readAsArrayBuffer(file);
  };

  const handleUpload = async () => {
    if (!excelData) {
      setMessage("❗ Моля, качете файл първо.");
      return;
    }
    try {
      const res = await fetch("http://localhost:3000/api/admin/upload", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ data: excelData }),
      });

      if (!res.ok) throw new Error("Upload failed");
      setMessage("✅ Данните са успешно записани в базата!");
    } catch (err) {
      console.error(err);
      setMessage("❌ Възникна грешка при записа.");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center px-4 py-12">
      <div className="bg-white shadow-2xl rounded-3xl p-8 max-w-2xl w-full transition-all duration-300">
        <h1 className="text-3xl font-extrabold text-center text-indigo-700 mb-8 flex items-center justify-center gap-2">
          <UploadCloud className="w-7 h-7 text-indigo-600" />
          Качете файл
        </h1>

        <div
          className="border-4 border-dashed border-indigo-300 rounded-xl p-6 text-center bg-indigo-50 hover:bg-indigo-100 transition cursor-pointer"
          onClick={() => fileInputRef.current.click()}
        >
          <input
            ref={fileInputRef}
            type="file"
            accept=".xlsx, .xls, .csv"
            onChange={handleFileUpload}
            className="hidden"
          />
          <p className="text-indigo-700 font-medium">
            {fileName
              ? `📄 ${fileName}`
              : "📂 Click or drag here to upload a file"}
          </p>
        </div>
        <div
          className="border-4 border-dashed border-indigo-300 rounded-xl p-6 text-center bg-indigo-50 hover:bg-indigo-100 transition cursor-pointer"
          onClick={() => fileInputRef.current.click()}
        >
          <input
            ref={fileInputRef}
            type="file"
            accept=".xlsx, .xls, .csv"
            onChange={handleFileUpload}
            className="hidden"
          />
          <p className="text-indigo-700 font-medium">
            {fileName
              ? `📄 ${fileName}`
              : "📂 Click or drag here to upload a file"}
          </p>
        </div>

        <button
          onClick={handleUpload}
          className="mt-6 w-full bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-semibold shadow-md"
        >
          📤 Запиши в базата
        </button>

        {message && (
          <div
            className={`mt-4 flex items-center gap-2 text-sm p-4 rounded-xl font-medium shadow ${
              message.includes("✅")
                ? "bg-green-100 text-green-700"
                : message.includes("❌")
                ? "bg-red-100 text-red-700"
                : "bg-yellow-100 text-yellow-700"
            }`}
          >
            {message.includes("✅") && <CheckCircle className="w-5 h-5" />}
            {message.includes("❌") && <XCircle className="w-5 h-5" />}
            <span>{message}</span>
          </div>
        )}

        {excelData && (
          <div className="mt-8">
            <h3 className="font-semibold text-indigo-700 mb-2 text-center">
              🔍 Преглед на съдържанието
            </h3>
            <div className="overflow-auto max-h-64 border border-indigo-200 rounded-lg bg-white shadow-inner">
              <table className="text-sm w-full border-collapse">
                <thead className="sticky top-0 bg-indigo-100 z-10">
                  <tr>
                    {Object.keys(excelData[0] || {}).map((key, i) => (
                      <th key={i} className="border px-3 py-2 font-semibold">
                        {key}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {excelData.map((row, rowIndex) => (
                    <tr key={rowIndex}>
                      {Object.values(row).map((val, colIndex) => (
                        <td key={colIndex} className="border px-3 py-1">
                          {val}
                        </td>
                      ))}
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
