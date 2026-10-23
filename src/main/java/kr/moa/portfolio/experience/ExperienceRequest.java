package kr.moa.portfolio.experience;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ExperienceRequest(
        @NotNull(message = "경험 종류를 선택해주세요.") ExperienceType type,
        @NotBlank(message = "제목을 입력해주세요.") @Size(max = 120) String title,
        @Size(max = 160) String organization,
        @Size(max = 160) String location,
        @NotNull(message = "시작일을 입력해주세요.") LocalDate startDate,
        LocalDate endDate,
        boolean ongoing,
        @Size(max = 300) String participants,
        @Size(max = 200) String role,
        @Size(max = 10000) String description,
        @Size(max = 1000) String outcome,
        @Size(max = 300) String tags,
        @Size(max = 1000) String link,
        boolean featured,
        @Size(max = 30, message = "분류 이름은 30자 이내로 입력해주세요.") String customCategory
) {}
