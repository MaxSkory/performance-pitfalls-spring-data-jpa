package performance.pitfalls.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import performance.pitfalls.config.MapperConfig;
import performance.pitfalls.dto.StudentResponseDto;
import performance.pitfalls.model.Student;
import performance.pitfalls.model.Subject;

@Mapper(config = MapperConfig.class)
public interface StudentMapper {

    @Mapping(source = "subjects", target = "subjectIds")
    StudentResponseDto toDto(Student student);

    default Set<Long> subjectsToIds(Set<Subject> subjects) {
        if (subjects == null) {
            return Collections.emptySet();
        }
        return subjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toSet());
    }

}
