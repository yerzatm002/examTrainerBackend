package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}