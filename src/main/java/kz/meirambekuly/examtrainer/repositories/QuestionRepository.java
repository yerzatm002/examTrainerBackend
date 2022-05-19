package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}