package kz.meirambekuly.examtrainer.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    @Size(min = 2, max = 150, message = "The question should be between 2 and 150 characters")
    @NotNull(message = "Question text not provided")
    private String text;
    private Long examId;
    private List<AnswerDto> answerDtoList;
    private AnswerDto correctAnswer;
    @Builder.Default
    private Double weight = 10.0;
    private Calendar createdDate;
}
