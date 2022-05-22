package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.ExamDto;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;

import java.util.List;

public interface ExamService {
    ResponseDto<?> save(ExamDto examDto);
    List<ExamDto> findAll();
    String remove(Long examId);
    ExamDto findById(Long examId);
    ResponseDto<?> update(Long id, ExamDto dto);
    List<QuestionDto> findQuestions(Long id);
}
