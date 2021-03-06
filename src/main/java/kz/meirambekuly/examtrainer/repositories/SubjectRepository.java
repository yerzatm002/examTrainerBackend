package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByTitle(String title);
}