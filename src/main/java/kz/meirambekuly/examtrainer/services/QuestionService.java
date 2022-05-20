package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;

import java.util.List;

public interface QuestionService {
    ResponseDto<?> save(QuestionDto dto);
    List<QuestionDto> findAll();
    String remove(Long questionId);
    QuestionDto findById(Long questionId);
    ResponseDto<?> update(QuestionDto dto);
}
