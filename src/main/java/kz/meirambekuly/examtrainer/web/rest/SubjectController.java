package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    //TODO: finish controllers

}
