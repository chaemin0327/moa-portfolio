package kr.moa.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:moa-test;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@Transactional
class PortfolioApiTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    private Map<String, Object> experience() {
        return new LinkedHashMap<>(Map.of(
                "type", "PROJECT", "title", "나의 테스트 프로젝트",
                "startDate", "2026-01-01", "endDate", "2026-03-01",
                "description", "API와 DB 저장을 확인합니다.",
                "participants", "팀원 2명", "featured", true));
    }

    private String create(Map<String, Object> data) throws Exception {
        return mvc.perform(post("/api/experiences").contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(data)))
                .andExpect(status().isCreated()).andReturn().getResponse().getHeader("Location");
    }

    @Test void createReadUpdateDelete() throws Exception {
        var data = experience();
        var location = create(data);
        mvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(data.get("title")))
                .andExpect(jsonPath("$.participants").value("팀원 2명"))
                .andExpect(jsonPath("$.demo").value(false));
        data.put("title", "수정된 프로젝트"); data.put("featured", false);
        mvc.perform(put(location).contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(data)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.featured").value(false));
        mvc.perform(get(location)).andExpect(jsonPath("$.title").value("수정된 프로젝트"));
        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test void rejectsMissingRequiredFields() throws Exception {
        mvc.perform(post("/api/experiences").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").exists());
    }

    @Test void rejectsReversedDates() throws Exception {
        var data = experience(); data.put("endDate", "2025-01-01");
        mvc.perform(post("/api/experiences").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(data)))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("종료일은 시작일 이후로 입력해주세요."));
    }

    @Test void ongoingExperienceDiscardsEndDate() throws Exception {
        var data = experience(); data.put("ongoing", true);
        mvc.perform(get(create(data))).andExpect(status().isOk())
                .andExpect(jsonPath("$.ongoing").value(true)).andExpect(jsonPath("$.endDate").isEmpty());
    }

    @Test void rejectsUnsafeLinksAndUnknownTypes() throws Exception {
        var data = experience(); data.put("link", "javascript:alert(1)");
        mvc.perform(post("/api/experiences").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
        data.remove("link"); data.put("type", "UNKNOWN");
        mvc.perform(post("/api/experiences").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test void removingDemoPreservesUserRecords() throws Exception {
        var location = create(experience());
        mvc.perform(delete("/api/experiences/demo")).andExpect(status().isNoContent());
        var result = mvc.perform(get("/api/experiences")).andExpect(status().isOk()).andReturn();
        var list = json.readTree(result.getResponse().getContentAsString());
        assertThat(list.size()).isEqualTo(1);
        mvc.perform(get(location)).andExpect(status().isOk());
    }

    @Test void updatesProfileAndValidatesEmail() throws Exception {
        var data = Map.of("name", "테스트 사용자", "headline", "개발자", "bio", "소개", "email", "test@example.com");
        mvc.perform(put("/api/profile").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(data)))
                .andExpect(status().isOk());
        mvc.perform(get("/api/profile")).andExpect(jsonPath("$.name").value("테스트 사용자"));
        mvc.perform(put("/api/profile").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"홍길동\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }
}
