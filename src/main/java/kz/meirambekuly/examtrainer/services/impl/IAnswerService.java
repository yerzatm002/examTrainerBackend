package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.Answer;
import kz.meirambekuly.examtrainer.repositories.AnswerRepository;
import kz.meirambekuly.examtrainer.repositories.QuestionRepository;
import kz.meirambekuly.examtrainer.services.AnswerService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.AnswerDto;
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
public class IAnswerService implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public ResponseDto<?> save(AnswerDto dto) {
        Optional<Answer> answer = answerRepository.findAnswerByText(dto.getText());
        if(answer.isPresent()){
            Answer newAnswer = Answer.builder()
                    .text(dto.getText())
                    .question(questionRepository.getById(dto.getQuestionId()))
                    .createdDate(dto.getCreatedAt())
                    .build();
            newAnswer = answerRepository.save(newAnswer);
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(newAnswer.getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Such answer dublicates: " + dto.getText())
                .build();
    }

    @Override
    public List<AnswerDto> findAll() {
        return answerRepository.findAll().stream().map(ObjectMapper::convertToAnswerDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String remove(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent()){
            answerRepository.deleteById(answerId);
            return "OK";
        }
        return "NOT VALID ID";
    }

    @Override
    public AnswerDto findById(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if(answer.isPresent()){
            return ObjectMapper.convertToAnswerDto(answer.get());
        }
        return AnswerDto.builder().build();
    }

    @Transactional
    @Override
    public ResponseDto<?> update(AnswerDto dto) {
        Optional<Answer> answer = answerRepository.findById(dto.getId());
        if (answer.isPresent()) {
            if(!dto.getText().isEmpty()){
                answer.get().setText(dto.getText());
            }
            if(Objects.nonNull(dto.getQuestionId())){
                answer.get().setQuestion(questionRepository.getById(dto.getQuestionId()));
            }
            answerRepository.save(answer.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(answer.get().getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("BAD REQUEST!")
                .build();
    }
}
