package kz.meirambekuly.examtrainer.services.impl;

import kz.meirambekuly.examtrainer.entities.Exam;
import kz.meirambekuly.examtrainer.entities.Subject;
import kz.meirambekuly.examtrainer.repositories.SubjectRepository;
import kz.meirambekuly.examtrainer.services.SubjectService;
import kz.meirambekuly.examtrainer.utils.ObjectMapper;
import kz.meirambekuly.examtrainer.web.dto.ExamDto;
import kz.meirambekuly.examtrainer.web.dto.ResponseDto;
import kz.meirambekuly.examtrainer.web.dto.SubjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ISubjectService implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public ResponseDto<?> save(SubjectDto subjectDto) {
        Optional<Subject> subject = subjectRepository.findByTitle(subjectDto.getTitle());
        if(subject.isEmpty()){
            Subject newSubject = Subject.builder()
                    .title(subjectDto.getTitle())
                    .createdDate(subjectDto.getCreatedDate())
                    .build();
            newSubject = subjectRepository.save(newSubject);
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(newSubject.getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Subject with such title " + subjectDto.getTitle() + " exists!")
                .build();
    }

    @Override
    public List<SubjectDto> findAll() {
        List<SubjectDto> dtos = subjectRepository.findAll().stream().map(ObjectMapper::convertToSubjectDto).collect(Collectors.toList());
        return dtos;
    }

    @Transactional
    @Override
    public String remove(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if (subject.isPresent()){
            subjectRepository.deleteById(subjectId);
            return "OK";
        }
        return "NOT VALID ID";
    }

    @Override
    public SubjectDto findById(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        if(subject.isPresent()){
            return ObjectMapper.convertToSubjectDto(subject.get());
        }
        return SubjectDto.builder().build();
    }

    @Transactional
    @Override
    public ResponseDto<?> update(SubjectDto dto, Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (subject.isPresent()) {
            if (!dto.getTitle().isEmpty()) {
                subject.get().setTitle(dto.getTitle());
            }
            subjectRepository.save(subject.get());
            return ResponseDto.builder()
                    .isSuccess(true)
                    .httpStatus(HttpStatus.OK.value())
                    .data(subject.get().getId())
                    .build();
        }
        return ResponseDto.builder()
                .isSuccess(false)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorMessage("BAD REQUEST!")
                .build();
    }

    @Override
    public List<ExamDto> findExams(Long id) {
        Optional<Subject> subject = subjectRepository.findById(id);
        return subject.get().getExams().stream().map(ObjectMapper::convertToExamDto).collect(Collectors.toList());
    }
}
