package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.ExamDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    ResponseDto<?> save(SubjectDto subjectDto);
    List<SubjectDto> findAll();
    String remove(Long subjectId);
    SubjectDto findById(Long subjectId);
    ResponseDto<?> update(SubjectDto dto, Long id);
    List<ExamDto> findExams(Long id);
}
