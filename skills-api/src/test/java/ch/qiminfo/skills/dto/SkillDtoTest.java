package ch.qiminfo.skills.dto;

import ch.qiminfo.skills.domain.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillDtoTest {

    private SkillDto skillDto;
    @BeforeEach
    void init() {
        skillDto = SkillDto.builder()
                .id(1L)
                .name("java")
                .version("17")
                .build();
    }

    @Test
    void testId() {
        assertNotNull(skillDto);
        assertEquals(1L, skillDto.getId());
        skillDto.setId(2L);
        assertEquals(2L, skillDto.getId());
    }

    @Test
    void testName() {
        assertNotNull(skillDto);
        assertEquals("java", skillDto.getName());
        skillDto.setName("sql");
        assertEquals("sql", skillDto.getName());
    }

    @Test
    void testVersion() {
        assertNotNull(skillDto);
        assertEquals("17", skillDto.getVersion());
        skillDto.setVersion("18");
        assertEquals("18", skillDto.getVersion());
    }

}