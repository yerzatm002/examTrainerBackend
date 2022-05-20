package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.Question;
import kz.meirambekuly.examtrainer.repositories.AnswerRepository;
import kz.meirambekuly.examtrainer.repositories.ExamRepository;
import kz.meirambekuly.examtrainer.repositories.QuestionRepository;
import kz.meirambekuly.examtrainer.services.QuestionService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IQuestionService implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final AnswerRepository answerRepository;

    @Override
    public ResponseDto<?> save(QuestionDto dto) {
        Optional<Question> question = questionRepository.findByText(dto.getText());
        if(question.isEmpty()){
            Question newQuestion = Question.builder()
                    .text(dto.getText())
                    .createdDate(dto.getCreatedDate())
                    .exam(examRepository.getById(dto.getExamId()))
                    .build();

            newQuestion = questionRepository.save(newQuestion);
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(newQuestion.getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Exam with such title " + dto.getText() + " exists!")
                .build();
    }

    @Override
    public List<QuestionDto> findAll() {
        return questionRepository.findAll().stream().map(ObjectMapper::convertToQuestionDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String remove(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()){
            questionRepository.deleteById(questionId);
            return "OK";
        }
        return "NOT VALID ID";
    }

    @Override
    public QuestionDto findById(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent()){
            return ObjectMapper.convertToQuestionDto(question.get());
        }
        return QuestionDto.builder().build();
    }

    @Transactional
    @Override
    public ResponseDto<?> update(QuestionDto dto) {
        Optional<Question> question = questionRepository.findById(dto.getId());
        if (question.isPresent()) {
            if(!dto.getText().isEmpty()){
                question.get().setText(dto.getText());
            }
            if(Objects.nonNull(dto.getCorrectAnswer())){
                question.get().setCorrectAnswer(answerRepository.getById(dto.getCorrectAnswer().getId()));
            }
            if(Objects.nonNull(dto.getAnswerDtoList())){
                question.get().setAnswers(dto.getAnswerDtoList().stream().map(ObjectMapper::convertToAnswer).collect(Collectors.toList()));
            }
            questionRepository.save(question.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(question.get().getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("BAD REQUEST!")
                .build();
    }
}
