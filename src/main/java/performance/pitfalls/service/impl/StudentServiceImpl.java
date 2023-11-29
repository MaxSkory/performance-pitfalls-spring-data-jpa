package performance.pitfalls.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import performance.pitfalls.dto.StudentResponseDto;
import performance.pitfalls.dto.StudentView;
import performance.pitfalls.mapper.StudentMapper;
import performance.pitfalls.model.Student;
import performance.pitfalls.repository.StudentRepository;
import performance.pitfalls.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentResponseDto> createAll(List<Student> students) {
        return studentRepository.saveAll(students).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    /**
     * Using EntityGraph/JOIN FETCH with lazy collection.
     * Processed request in one step.
     * Resolves N+1 problem.
     * May produce OutOfMemoryException, Cartesian product problem.
     * Bad performance produced by
     * HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
     */
    @Override
    public List<StudentResponseDto> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    /**
     * Using EntityGraph/JOIN FETCH with lazy collection.
     * Processed request in two steps.
     * Resolves N+1 and Cartesian product problems
     * May produce OutOfMemoryException ???
     * May produce SQL exception (depends on IN statement size)
     */
    @Override
    public List<StudentResponseDto> getAllInTwoSteps(Pageable pageable) {
        List<Long> studentIds = studentRepository.findAllIds(pageable).getContent();
        return studentRepository.findAllByIdIn(studentIds).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Override
    public List<StudentView> getAllNames() {
        return studentRepository.findAllNames();
    }
}
