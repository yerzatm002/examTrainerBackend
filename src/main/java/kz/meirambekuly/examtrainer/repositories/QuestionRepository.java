package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByText(String text);
}