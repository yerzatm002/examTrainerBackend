package kz.meirambekuly.examtrainer.utils;

import kz.meirambekuly.examtrainer.entities.*;
import kz.meirambekuly.examtrainer.web.dto.*;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class ObjectMapper {
    public static UserDto convertToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .roleDto(convertToRoleDto(user.getRole()))
                .build();
    }

    public static RoleDto convertToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static SubjectDto convertToSubjectDto(Subject subject){
        return SubjectDto.builder()
                .id(subject.getId())
                .title(subject.getTitle())
                .numbOfExams(subject.getExams().size())
                .createdDate(subject.getCreatedDate())
                .build();
    }

    public static ExamDto convertToExamDto(Exam exam){
        return ExamDto.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .subjectId(exam.getSubject().getId())
                .numbOfQuestions(exam.getQuestions().size())
                .createdDate(exam.getCreatedDate())
                .build();
    }

    public static QuestionDto convertToQuestionDto(Question question){
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .examId(question.getExam().getId())
                .weight(question.getWeight())
                .answerDtoList(question.getAnswers().stream().map(ObjectMapper::convertToAnswerDto).collect(Collectors.toList()))
                .correctAnswer(convertToAnswerDto(question.getCorrectAnswer()))
                .createdDate(question.getCreatedDate())
                .build();
    }

    public static AnswerDto convertToAnswerDto(Answer answer){
        return AnswerDto.builder()
                .id(answer.getId())
                .createdAt(answer.getCreatedDate())
                .text(answer.getText())
                .questionId(answer.getQuestion().getId())
                .build();
    }

    public static Answer convertToAnswer(AnswerDto answerDto){
        return Answer.builder()
                .id(answerDto.getId())
                .text(answerDto.getText())
                .createdDate(answerDto.getCreatedAt())
                .build();
    }

}
