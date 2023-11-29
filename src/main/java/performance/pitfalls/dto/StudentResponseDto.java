package performance.pitfalls.dto;

import java.util.Set;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        Set<Long> subjectIds
) {
}
