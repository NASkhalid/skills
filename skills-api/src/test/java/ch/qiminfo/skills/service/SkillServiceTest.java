package ch.qiminfo.skills.service;

import ch.qiminfo.skills.domain.Skill;
import ch.qiminfo.skills.dto.SkillDto;
import ch.qiminfo.skills.mapper.SkillMapper;
import ch.qiminfo.skills.repository.SkillRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillServiceTest {

    @InjectMock
    SkillRepository repository;
    @InjectMock
    SkillMapper mapper;

    @Inject
    SkillService service;

    private Skill skill;
    private SkillDto skillDto;
    @BeforeEach
    void setUp() {
        skill = Skill.builder()
                .id(1L)
                .name("java")
                .version("17")
                .build();
        skillDto = SkillDto.builder()
                .id(1L)
                .name("java")
                .version("17")
                .build();
    }

    @Test
    @Order(1)
    void getAllSkills() {
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        when(repository.listAll()).thenReturn(skills);
        when(mapper.toDto(skill)).thenReturn(skillDto);
        List<SkillDto> allSkillsDto = service.getAllSkills();
        assertNotNull(allSkillsDto);
        assertFalse(allSkillsDto.isEmpty());
        assertEquals(1L, allSkillsDto.get(0).getId());
        assertEquals("java", allSkillsDto.get(0).getName());
        assertEquals("17", allSkillsDto.get(0).getVersion());
    }

    @Test
    @Order(1)
    void findByVersion_OK() {
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        when(repository.findByVersion("17")).thenReturn(skills);
        when(mapper.toDto(skill)).thenReturn(skillDto);
        List<SkillDto> allSkillsDto = service.findByVersion("17");
        assertNotNull(allSkillsDto);
        assertFalse(allSkillsDto.isEmpty());
        assertEquals(1L, allSkillsDto.get(0).getId());
        assertEquals("java", allSkillsDto.get(0).getName());
        assertEquals("17", allSkillsDto.get(0).getVersion());
    }

    @Test
    @Order(1)
    void findByName_OK() {

        PanacheQuery<Skill> query = mock(PanacheQuery.class);
        when(query.page(any())).thenReturn(query);
        when(query.singleResultOptional()).thenReturn(Optional.of(skill));

        when(repository.find("name", "java")).thenReturn(query);
        when(mapper.toDto(skill)).thenReturn(skillDto);

        SkillDto skillDto = service.findByUniqueName("java");
        assertNotNull(skillDto);
        assertEquals(1L, skillDto.getId());
        assertEquals("java", skillDto.getName());
        assertEquals("17", skillDto.getVersion());

    }

    @Test
    @Order(1)
    void findByName_KO() {

        PanacheQuery<Skill> query = mock(PanacheQuery.class);
        when(query.page(any())).thenReturn(query);
        when(query.singleResultOptional()).thenReturn(Optional.empty());

        when(repository.find("name", "name not exist")).thenReturn(query);

        SkillDto skillDto = service.findByUniqueName("name not exist");
        assertNull(skillDto);
    }

    @Test
    @Order(2)
    void createSkill_OK() {
        SkillDto skillDto = SkillDto.builder()
                .id(1L)
                .name("angular")
                .version("12")
                .build();

        Skill skill = Skill.builder()
                .id(1L)
                .name("angular")
                .version("12")
                .build();
        when(mapper.toEntity(skillDto)).thenReturn(skill);

        doNothing().when(repository).persist(
                ArgumentMatchers.any(Skill.class)
        );
        when(repository.isPersistent(
                ArgumentMatchers.any(Skill.class)
        )).thenReturn(true);

        when(mapper.toDto(skill)).thenReturn(skillDto);

        SkillDto response = service.createSkill(skillDto);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("angular", response.getName());
        assertEquals("12", response.getVersion());
    }

    @Test
    @Order(2)
    void createSkill_KO() {
        SkillDto skillDto = SkillDto.builder()
                .id(1L)
                .name("angular")
                .version("12")
                .build();

        Skill skill = Skill.builder()
                .id(1L)
                .name("angular")
                .version("12")
                .build();
        when(mapper.toEntity(skillDto)).thenReturn(skill);

        doNothing().when(repository).persist(
                ArgumentMatchers.any(Skill.class)
        );
        when(repository.isPersistent(
                ArgumentMatchers.any(Skill.class)
        )).thenReturn(false);


        SkillDto response = service.createSkill(skillDto);
        assertNull(response);

    }

    @Test
    @Order(3)
    void updateSkill_OK() {
        SkillDto skillDto = SkillDto.builder()
                .id(1L)
                .name("valeur update")
                .version("12")
                .build();

        Skill skill = Skill.builder()
                .id(1L)
                .name("angular")
                .version("12")
                .build();
        when(repository.findByIdOptional(1L)).thenReturn(Optional.of(skill));
        when(mapper.toDto(skill)).thenReturn(skillDto);

        SkillDto response = service.updateSkill(1L, skillDto);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("valeur update", response.getName());
        assertEquals("12", response.getVersion());
    }

    @Test
    @Order(3)
    void updateSkill_KO() {
        when(repository.findByIdOptional(1L)).thenReturn(Optional.empty());

        SkillDto response = service.updateSkill(1L, skillDto);
        assertNull(response);
    }


    @Test
    @Order(4)
    void deleteSkill_OK() {
        when(repository.deleteById(1L)).thenReturn(true);
        boolean response = service.deleteSkill(1L);
        assertNotNull(response);
        assertEquals(true, response);
    }

    @Test
    @Order(4)
    void deleteSkill_KO() {
        when(repository.deleteById(1L)).thenReturn(false);
        boolean response = service.deleteSkill(1L);
        assertNotNull(response);
        assertEquals(false, response);
    }

}