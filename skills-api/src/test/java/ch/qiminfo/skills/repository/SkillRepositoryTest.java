package ch.qiminfo.skills.repository;

import ch.qiminfo.skills.domain.Skill;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class SkillRepositoryTest {
    @Inject
    SkillRepository repository;
    @Test
    void findByVersionOK() {
        List<Skill> skills = repository.findByVersion("11");
        assertNotNull(skills);
        assertFalse(skills.isEmpty());
        assertEquals(1, skills.size());
        assertEquals(2, skills.get(0).getId());
        assertEquals("java", skills.get(0).getName());
        assertEquals("11", skills.get(0).getVersion());
    }


}