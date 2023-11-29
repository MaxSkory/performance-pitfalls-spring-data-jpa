package performance.pitfalls.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import performance.pitfalls.dto.StudentResponseDto;
import performance.pitfalls.dto.StudentView;
import performance.pitfalls.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perform/students")
@Log4j2
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<StudentResponseDto> getAll(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        List<StudentResponseDto> result = studentService.getAll(pageable);
        log.info("Processing time for retrieving data using "
                        + "@EntityGraph/JOIN FETCH in one step: {} ms",
                System.currentTimeMillis() - startTime);
        return result;
    }

    @GetMapping("/two_steps")
    public List<StudentResponseDto> getAllInTwoSteps(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        List<StudentResponseDto> result = studentService.getAllInTwoSteps(pageable);
        log.info("Processing time for retrieving data USING"
                        + " @EntityGraph/JOIN FETCH in two steps: {} ms",
                System.currentTimeMillis() - startTime);
        return result;
    }

    @GetMapping("/names")
    public List<StudentView> getAllNames(){
        return studentService.getAllNames();
    }
}
