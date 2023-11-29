package performance.pitfalls.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import performance.pitfalls.dto.SubjectResponseDto;
import performance.pitfalls.model.Subject;

public interface SubjectService {
    List<SubjectResponseDto> createAll(List<Subject> subjects);

    List<SubjectResponseDto> getAll(Pageable pageable);

    void lock(Long time);
}
