package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.AnswerDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;

import java.util.List;

public interface AnswerService {
    ResponseDto<?> save(AnswerDto dto);
    List<AnswerDto> findAll();
    String remove(Long answerId);
    AnswerDto findById(Long answerId);
    ResponseDto<?> update(AnswerDto dto);
}
