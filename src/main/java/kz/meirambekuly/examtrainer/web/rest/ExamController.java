package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.ExamService;
import kz.meirambekuly.examtrainer.web.dto.ExamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody ExamDto examDto){
        return ResponseEntity.ok(examService.save(examDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll (){
        return ResponseEntity.ok(examService.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        return ResponseEntity.ok(examService.findById(id));
    }

    @GetMapping("/questions")
    public ResponseEntity<?> findQuestions(@RequestParam("id") Long id){
        return ResponseEntity.ok(examService.findQuestions(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestBody ExamDto examDto){
        return ResponseEntity.ok(examService.update(id, examDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        return ResponseEntity.ok(examService.remove(id));
    }
}
