package kr.moa.portfolio.experience;

import java.time.LocalDate;

public record ExperienceView(Long id, ExperienceType type, String title, String organization,
        String location, LocalDate startDate, LocalDate endDate, boolean ongoing,
        String participants, String role, String description, String outcome,
        String tags, String link, boolean featured, boolean demo, String customCategory) {
    static ExperienceView from(Experience e) {
        return new ExperienceView(e.id, e.type, e.title, e.organization, e.location,
                e.startDate, e.endDate, e.ongoing, e.participants, e.role,
                e.description, e.outcome, e.tags, e.link, e.featured, e.demo,
                e.customCategory == null ? "" : e.customCategory);
    }
}
