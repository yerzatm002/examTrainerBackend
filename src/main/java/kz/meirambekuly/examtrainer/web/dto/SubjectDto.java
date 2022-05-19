package kz.meirambekuly.examtrainer.web.dto;

import lombok.*;

import java.util.Calendar;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    private Long id;
    private String title;
    private Calendar createdDate;
}
