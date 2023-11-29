package performance.pitfalls.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import performance.pitfalls.dto.SubjectResponseDto;
import performance.pitfalls.mapper.SubjectMapper;
import performance.pitfalls.model.Subject;
import performance.pitfalls.repository.SubjectRepository;
import performance.pitfalls.service.SubjectService;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public List<SubjectResponseDto> createAll(List<Subject> subjects) {
        return subjectRepository.saveAll(subjects).stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    /**
     * Using @BatchSize with lazy collection.
     * Processed request in N/M+1 steps.
     * Resolve Cartesian product problem
     * Only masks N+1 problem. We have N/M+1 problem instead.
     * May lock DB while using @Transactional ???
     */
    @Override
//    @Transactional(readOnly = true)
    public List<SubjectResponseDto> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable).stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void lock(Long time) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        while (stopWatch.getTime(TimeUnit.SECONDS) < time) {
            subjectRepository.findAll();
        }
    }
}
