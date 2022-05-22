package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Exam;
import kz.meirambekuly.examtrainer.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByTitle(String title);

}