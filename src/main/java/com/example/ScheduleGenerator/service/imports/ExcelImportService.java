package com.example.ScheduleGenerator.service.imports;

import com.example.ScheduleGenerator.models.Semester;
import com.example.ScheduleGenerator.models.Subject;
import com.example.ScheduleGenerator.models.Teacher;
import com.example.ScheduleGenerator.models.TeachingAssignment;
import com.example.ScheduleGenerator.models.enums.SubjectType;
import com.example.ScheduleGenerator.repository.SemesterRepository;
import com.example.ScheduleGenerator.repository.SubjectRepository;
import com.example.ScheduleGenerator.repository.TeacherRepository;
import com.example.ScheduleGenerator.repository.TeachingAssignmentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelImportService {

    @Autowired private SubjectRepository subjectRepo;
    @Autowired private TeacherRepository teacherRepo;
    @Autowired private SemesterRepository semesterRepo;
    @Autowired private TeachingAssignmentRepository assignmentRepo;

    public void importFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Semester currentSemester = null;
        boolean skipHeader = true;

        for (Row row : sheet) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }

            Cell firstCell = row.getCell(0);
            if (firstCell == null || firstCell.getCellType() != CellType.STRING) continue;

            String cellValue = firstCell.getStringCellValue().trim();

            if (cellValue.startsWith("СЕМЕСТЪР")) {
                currentSemester = getOrCreateSemester(cellValue);
            } else if (!cellValue.isEmpty() && currentSemester != null) {
                String subjectName = cellValue;
                Subject subject = new Subject();
                subject.setName(subjectName);
                subject.setSemester(currentSemester);
                subject = subjectRepo.save(subject);


                SubjectType[] types = {SubjectType.ЛЕКЦИИ, SubjectType.СЕМИНАРНИ, SubjectType.ЛАБОРАТОРНИ};
                int[] cols = {1, 2, 3};

                for (int i = 0; i < cols.length; i++) {
                    String teacherCell = getCellValue(row, cols[i]);
                    if (teacherCell == null || teacherCell.equals("0") || teacherCell.isBlank()) continue;
                    assignTeachers(teacherCell, subject, types[i]);
                }
            }
        }

        workbook.close();
    }

    private Semester getOrCreateSemester(String semesterNumber) {
        return semesterRepo.findBySemesterNo(semesterNumber).orElseGet(() -> {
            Semester s = new Semester();
            s.setSemesterNo(semesterNumber);
            return semesterRepo.save(s);
        });
    }

    private void assignTeachers(String rawCell, Subject subject, SubjectType type) {
        String[] names = rawCell.split(",");
        for (String name : names) {
            String trimmed = name.trim();
            if (trimmed.isBlank()) continue;

            Teacher teacher = teacherRepo.findByName(trimmed)
                    .orElseGet(() -> {
                        Teacher t = new Teacher();
                        t.setName(trimmed);
                        return teacherRepo.save(t);
                    });

            TeachingAssignment assignment = new TeachingAssignment();
            assignment.setSubject(subject);
            assignment.setTeacher(teacher);
            assignment.setType(type);
            assignmentRepo.save(assignment);
        }
    }

    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> null;
        };
    }
}

