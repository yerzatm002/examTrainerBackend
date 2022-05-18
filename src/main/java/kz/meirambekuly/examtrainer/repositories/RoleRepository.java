package kz.meirambekuly.examtrainer.repositories;

import kz.meirambekuly.examtrainer.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
