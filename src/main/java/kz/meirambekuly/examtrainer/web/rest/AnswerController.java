package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.AnswerService;
import kz.meirambekuly.examtrainer.web.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AnswerDto answerDto){
        return ResponseEntity.ok(answerService.save(answerDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(answerService.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@RequestParam("id")Long id){
        return ResponseEntity.ok(answerService.findById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id")Long id,
                                    @RequestBody AnswerDto answerDto){
        return ResponseEntity.ok(answerService.update(id, answerDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id")Long id){
        return ResponseEntity.ok(answerService.remove(id));
    }

}
