package performance.pitfalls;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import performance.pitfalls.model.Student;
import performance.pitfalls.model.Subject;
import performance.pitfalls.service.StudentService;
import performance.pitfalls.service.SubjectService;

@SpringBootApplication
@RequiredArgsConstructor
public class PerformancePitfallsSpringDataJpaApplication {
    private final SubjectService subjectService;
    private final StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(PerformancePitfallsSpringDataJpaApplication.class, args);
    }

//    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Faker faker = new Faker(Locale.US);
            List<Subject> subjects = Stream.generate(() ->
                            Subject.builder().name(faker.educator().course()).build())
                    .limit(100)
                    .toList();
            subjectService.createAll(subjects);
            List<Student> students = Stream.generate(() ->
                            Student.builder()
                                    .firstName(faker.name().firstName())
                                    .lastName(faker.name().lastName())
                                    .subjects(Set.copyOf(subjects)).build())
                    .limit(100)
                    .toList();
            studentService.createAll(students);
        };
    }
}
