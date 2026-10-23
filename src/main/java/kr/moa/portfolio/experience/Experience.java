package kr.moa.portfolio.experience;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    ExperienceType type;
    @Column(nullable = false, length = 120) String title;
    @Column(length = 160) String organization;
    @Column(length = 160) String location;
    @Column(nullable = false) LocalDate startDate;
    LocalDate endDate;
    boolean ongoing;
    @Column(length = 300) String participants;
    @Column(length = 200) String role;
    @Column(length = 10000) String description;
    @Column(length = 1000) String outcome;
    @Column(length = 300) String tags;
    @Column(length = 1000) String link;
    boolean featured;
    boolean demo;

    protected Experience() {}

    public Experience(ExperienceRequest request, boolean demo) {
        update(request);
        this.demo = demo;
    }

    public void update(ExperienceRequest r) {
        type = r.type(); title = clean(r.title()); organization = clean(r.organization());
        location = clean(r.location()); startDate = r.startDate();
        endDate = r.ongoing() ? null : r.endDate(); ongoing = r.ongoing();
        participants = clean(r.participants()); role = clean(r.role());
        description = clean(r.description()); outcome = clean(r.outcome());
        tags = clean(r.tags()); link = clean(r.link()); featured = r.featured();
    }

    private static String clean(String value) { return value == null ? "" : value.strip(); }
}
