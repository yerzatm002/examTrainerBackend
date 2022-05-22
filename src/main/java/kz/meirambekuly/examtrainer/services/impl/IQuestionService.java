package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.Answer;
import kz.meirambekuly.examtrainer.entities.Question;
import kz.meirambekuly.examtrainer.repositories.AnswerRepository;
import kz.meirambekuly.examtrainer.repositories.ExamRepository;
import kz.meirambekuly.examtrainer.repositories.QuestionRepository;
import kz.meirambekuly.examtrainer.services.QuestionService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.AnswerDto;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Transactional
    @Override
    public ResponseDto<?> save(QuestionDto dto) {
        Optional<Question> question = questionRepository.findByText(dto.getText());
        List<Answer> answers = new ArrayList<>();
        if(question.isEmpty()){
            Question newQuestion = Question.builder()
                    .text(dto.getText())
                    .createdDate(dto.getCreatedDate())
                    .exam(examRepository.getById(dto.getExamId()))
                    .weight(dto.getWeight())
                    .build();
            newQuestion = questionRepository.save(newQuestion);
            if(!dto.getAnswerDtoList().isEmpty()){
                for(AnswerDto answerDto : dto.getAnswerDtoList()){
                    Answer answer = Answer.builder()
                            .text(answerDto.getText())
                            .question(newQuestion)
                            .build();
                    answer = answerRepository.save(answer);
                    answers.add(answer);
                }
                newQuestion.setAnswers(answers);
                questionRepository.save(newQuestion);
            }
            if (Objects.nonNull(dto.getCorrectAnswer())){
                Answer correctAnswer = Answer.builder()
                        .text(dto.getCorrectAnswer().getText())
                        .question(newQuestion)
                        .build();
                correctAnswer = answerRepository.save(correctAnswer);
                newQuestion.setCorrectAnswer(correctAnswer);
                questionRepository.save(newQuestion);
            }
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
    public ResponseDto<?> update(Long id, QuestionDto dto) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            if(!dto.getText().isEmpty()){
                question.get().setText(dto.getText());
            }
            if(!dto.getWeight().isNaN()){
                question.get().setWeight(dto.getWeight());
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

    @Override
    public List<AnswerDto> findAnswers(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.get().getAnswers().stream().map(ObjectMapper :: convertToAnswerDto).collect(Collectors.toList());
    }
}
