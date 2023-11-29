package performance.pitfalls.dto;

import java.util.Set;

public record SubjectResponseDto(
        Long id,
        String name,
        Set<Long> studentIds
) {
}
