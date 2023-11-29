package performance.pitfalls.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import performance.pitfalls.dto.StudentResponseDto;
import performance.pitfalls.dto.StudentView;
import performance.pitfalls.model.Student;

public interface StudentService {
    List<StudentResponseDto> createAll(List<Student> students);

    List<StudentResponseDto> getAll(Pageable pageable);

    List<StudentResponseDto> getAllInTwoSteps(Pageable pageable);

    List<StudentView> getAllNames();
}
