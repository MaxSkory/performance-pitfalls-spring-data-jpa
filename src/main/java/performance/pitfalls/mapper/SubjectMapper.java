package performance.pitfalls.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import performance.pitfalls.config.MapperConfig;
import performance.pitfalls.dto.SubjectResponseDto;
import performance.pitfalls.model.Student;
import performance.pitfalls.model.Subject;

@Mapper(config = MapperConfig.class)
public interface SubjectMapper {
    @Mapping(source = "students", target = "studentIds")
    SubjectResponseDto toDto(Subject subject);

     default Set<Long> studentsToIds(Set<Student> students) {
        if (students == null){
            return Collections.emptySet();
        }
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
    }
}
