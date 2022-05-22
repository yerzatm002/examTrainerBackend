package kz.meirambekuly.examtrainer.web.dto;

import lombok.*;

import java.util.Calendar;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private Long id;
    private String title;
    private Long subjectId;
    private Integer numbOfQuestions;
    private Calendar createdDate;
}
