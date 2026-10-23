package kr.moa.portfolio.experience;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    long deleteByDemoTrue();
}
