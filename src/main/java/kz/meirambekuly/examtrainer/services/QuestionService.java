package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.AnswerDto;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;

import java.util.List;

public interface QuestionService {
    ResponseDto<?> save(QuestionDto dto);
    List<QuestionDto> findAll();
    String remove(Long questionId);
    QuestionDto findById(Long questionId);
    ResponseDto<?> update(Long id, QuestionDto dto);
    List<AnswerDto> findAnswers(Long id);
}
