package kz.meirambekuly.examtrainer.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Long id;
    private Long questionId;
    @Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
    @NotNull(message = "No answer text provided.")
    private String text;
    private Calendar createdAt;
}
