package kz.meirambekuly.examtrainer.web.rest;

import kz.meirambekuly.examtrainer.services.SubjectService;
import kz.meirambekuly.examtrainer.web.dto.SubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SubjectDto subjectDto){
        return ResponseEntity.ok(subjectService.save(subjectDto));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SubjectDto subjectDto){
        return ResponseEntity.ok(subjectService.update(subjectDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        return ResponseEntity.ok(subjectService.remove(id));
    }
}
