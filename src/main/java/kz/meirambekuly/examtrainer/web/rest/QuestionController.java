package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.QuestionService;
import kz.meirambekuly.examtrainer.web.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody QuestionDto questionDto){
        return ResponseEntity.ok(questionService.save(questionDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll (){
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    @GetMapping("/answers")
    public ResponseEntity<?> findAnswers(@RequestParam("id") Long id){
        return ResponseEntity.ok(questionService.findAnswers(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Long id,
                                    @RequestBody QuestionDto questionDto){
        return ResponseEntity.ok(questionService.update(id, questionDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        return ResponseEntity.ok(questionService.remove(id));
    }

}
