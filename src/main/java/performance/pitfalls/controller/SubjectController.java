package performance.pitfalls.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import performance.pitfalls.dto.SubjectResponseDto;
import performance.pitfalls.service.SubjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perform/subjects")
@Log4j2
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectResponseDto> getAll(Pageable pageable) {
        long startTime = System.currentTimeMillis();
        List<SubjectResponseDto> result = subjectService.getAll(pageable);
        log.info("Processing time for retrieving data using "
                        + "@BatchSize & @Transactional: {} ms",
                System.currentTimeMillis() - startTime);
        return result;
    }

    //    @GetMapping
//    public void getAll(Pageable pageable) {
//        long startTime = System.currentTimeMillis();
//        List<Subject> subjectsFromDb = subjectService.getAll(pageable);
//    }

    @GetMapping("/lock")
    public String lock(@RequestParam Long time) {
        subjectService.lock(time);
        return "Locking finished";
    }
}
