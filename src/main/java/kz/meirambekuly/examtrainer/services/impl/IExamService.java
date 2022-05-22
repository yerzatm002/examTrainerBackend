package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.Exam;
import kz.meirambekuly.examtrainer.entities.Subject;
import kz.meirambekuly.examtrainer.repositories.ExamRepository;
import kz.meirambekuly.examtrainer.repositories.SubjectRepository;
import kz.meirambekuly.examtrainer.services.ExamService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.ExamDto;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IExamService implements ExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public ResponseDto<?> save(ExamDto examDto) {
        Optional<Exam> exam = examRepository.findByTitle(examDto.getTitle());
        Optional<Subject> subject = subjectRepository.findById(examDto.getSubjectId());
        if(exam.isEmpty() && subject.isPresent()) {
            Exam newExam = Exam.builder()
                    .title(examDto.getTitle())
                    .subject(subject.get())
                    .createdDate(examDto.getCreatedDate())
                    .build();
            newExam = examRepository.save(newExam);
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(newExam.getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Exam with such title " + examDto.getTitle() + " exists!")
                .build();
    }

    @Override
    public List<ExamDto> findAll() {
        return examRepository.findAll().stream().map(ObjectMapper::convertToExamDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String remove(Long examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        if (exam.isPresent()){
            examRepository.deleteById(examId);
            return "OK";
        }
        return "NOT VALID ID";
    }

    @Override
    public ExamDto findById(Long examId) {
        Optional<Exam> exam = examRepository.findById(examId);
        if(exam.isPresent()){
            return ObjectMapper.convertToExamDto(exam.get());
        }
        return ExamDto.builder().build();
    }

    @Transactional
    @Override
    public ResponseDto<?> update(Long id, ExamDto dto) {
        Optional<Exam> exam = examRepository.findById(id);
        if (exam.isPresent()) {
            if (!dto.getTitle().isEmpty()) {
                exam.get().setTitle(dto.getTitle());
            }
            examRepository.save(exam.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(exam.get().getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("BAD REQUEST!")
                .build();
    }

    @Override
    public List<QuestionDto> findQuestions(Long id) {
        Optional<Exam> exam = examRepository.findById(id);
        return exam.get().getQuestions().stream().map(ObjectMapper::convertToQuestionDto).collect(Collectors.toList());
    }
}
