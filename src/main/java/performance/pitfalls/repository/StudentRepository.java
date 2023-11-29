package performance.pitfalls.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import performance.pitfalls.dto.StudentView;
import performance.pitfalls.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @NonNull
    @Query(value = "FROM Student s LEFT JOIN FETCH s.subjects")
//    @EntityGraph(attributePaths = "subjects")
    Page<Student> findAll(@NonNull Pageable pageable);

    @Query("SELECT s.id FROM Student s")
    Page<Long> findAllIds(Pageable pageable);

    @EntityGraph(attributePaths = "subjects")
    List<Student> findAllByIdIn(List<Long> ids);

    @Query("SELECT s.id as id, s.firstName as firstName FROM Student s")
    List<StudentView> findAllNames();
}
