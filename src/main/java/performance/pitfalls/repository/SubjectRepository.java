package performance.pitfalls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import performance.pitfalls.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
