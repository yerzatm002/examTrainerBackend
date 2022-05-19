package kz.meirambekuly.examtrainer.services;

import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.SubjectDto;

public interface SubjectService {
    ResponseDto save(SubjectDto subjectDto);
}
