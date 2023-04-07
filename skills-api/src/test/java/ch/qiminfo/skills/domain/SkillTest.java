package ch.qiminfo.skills.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SkillTest {

    private Skill skill;
    @BeforeEach
    void init() {
        skill = Skill.builder()
                .id(1L)
                .name("java")
                .version("17")
                .build();
    }

    @Test
    void testId() {
        assertNotNull(skill);
        assertEquals(1L,skill.getId());
        skill.setId(2L);
        assertEquals(2L, skill.getId());
    }

    @Test
    void testName() {
        assertNotNull(skill);
        assertEquals("java",skill.getName());
        skill.setName("sql");
        assertEquals("sql", skill.getName());
    }

    @Test
    void testVersion() {
        assertNotNull(skill);
        assertEquals("17",skill.getVersion());
        skill.setVersion("18");
        assertEquals("18", skill.getVersion());
    }
}