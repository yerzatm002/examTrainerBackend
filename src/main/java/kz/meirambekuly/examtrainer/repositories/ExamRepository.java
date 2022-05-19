package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}