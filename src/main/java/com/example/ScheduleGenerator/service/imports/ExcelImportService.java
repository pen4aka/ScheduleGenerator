package com.example.ScheduleGenerator.service.imports;

import com.example.ScheduleGenerator.models.*;
import com.example.ScheduleGenerator.models.enums.SubjectType;
import com.example.ScheduleGenerator.repository.*;
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
    @Autowired private SubjectScheduleInfoRepository scheduleInfoRepo;
    @Autowired private StudentGroupRepository groupRepo;

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

                processAssignment(row.getCell(1), subject, SubjectType.ЛЕКЦИИ);
                processAssignment(row.getCell(2), subject, SubjectType.СЕМИНАРНИ);
                processAssignment(row.getCell(3), subject, SubjectType.ЛАБОРАТОРНИ);

                processScheduleInfo(subject, SubjectType.ЛЕКЦИИ, getInt(row.getCell(4)), currentSemester);
                processScheduleInfo(subject, SubjectType.СЕМИНАРНИ, getInt(row.getCell(5)), currentSemester);
                processScheduleInfo(subject, SubjectType.ЛАБОРАТОРНИ, getInt(row.getCell(6)), currentSemester);

                importGroups(row.getCell(7));
            }
        }

        workbook.close();
    }

    private void importGroups(Cell groupCell) {
        if (groupCell == null) return;
        String value = getCellValue(groupCell);
        if (value == null || value.equals("0") || value.isBlank()) return;

        for (String token : value.split(",")) {
            String trimmed = token.trim();
            if (trimmed.isBlank()) continue;
            try {
                long groupNumber = Long.parseLong(trimmed);
                boolean exists = groupRepo.findAll().stream()
                        .anyMatch(g -> g.getNameGroup().equals(groupNumber));
                if (!exists) {
                    StudentGroup group = new StudentGroup();
                    group.setNameGroup(groupNumber);
                    group.setStudentCount(30);
                    groupRepo.save(group);
                }
            } catch (NumberFormatException ignored) {}
        }
    }

    private void processAssignment(Cell teacherCell, Subject subject, SubjectType type) {
        String value = getCellValue(teacherCell);
        if (value == null || value.equals("0") || value.isBlank()) return;

        for (String name : value.split(",")) {
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

    private void processScheduleInfo(Subject subject, SubjectType type, Integer hours, Semester semester) {
        if (hours == null || hours == 0) return;

        int duration, frequency, weeks;
        int semesterWeeks = semester.getSemesterNo().contains("8") ? 10 : 15;

        if (hours == 45) {
            duration = 3; frequency = 1; weeks = 15;
        } else if (hours == 30) {
            duration = 2; frequency = 1; weeks = 15;
        } else if (hours == 15) {
            duration = 2; frequency = 2; weeks = 15;
        } else if (hours == 20) {
            duration = 2; frequency = 1; weeks = 10;
        } else if (hours == 10) {
            duration = 2; frequency = 2; weeks = 10;
        } else {
            return; // unsupported value
        }

        SubjectScheduleInfo info = new SubjectScheduleInfo();
        info.setSubject(subject);
        info.setType(type);
        info.setTotalHours(hours);
        info.setDurationPerSession(duration);
        info.setWeeksFrequency(frequency);
        info.setTotalWeeks(weeks);

        scheduleInfoRepo.save(info);
    }

    private Semester getOrCreateSemester(String name) {
        return semesterRepo.findBySemesterNo(name).orElseGet(() -> {
            Semester s = new Semester();
            s.setSemesterNo(name);
            return semesterRepo.save(s);
        });
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> null;
        };
    }

    private Integer getInt(Cell cell) {
        if (cell == null) return null;
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }

    @Autowired
    private RoomRepository roomRepository;

    public void importRoomsFromExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        boolean skipHeader = true;

        for (Row row : sheet) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }

            String name = getCellValue(row.getCell(0));
            Integer capacity = getInt(row.getCell(1));
            String typeText = getCellValue(row.getCell(2));

            SubjectType subjectType = switch (typeText) {
                case "Л" -> SubjectType.ЛЕКЦИИ;
                case "СУ" -> SubjectType.СЕМИНАРНИ;
                case "ЛУ" -> SubjectType.ЛАБОРАТОРНИ;
                default -> null;
            };

            if (name != null && capacity != null && subjectType != null) {
                Room room = new Room();
                room.setName(name);
                room.setCapacity(capacity);
                room.setType(subjectType);
                roomRepository.save(room);
            }
        }
        workbook.close();
    }
}


