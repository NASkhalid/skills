package ch.qiminfo.skills.controller;

import ch.qiminfo.skills.dto.SkillDto;
import ch.qiminfo.skills.service.SkillService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillControllerTest {

    @InjectMock
    SkillService service;
    @Inject
    SkillController controller;


    private SkillDto skillDto;
    @BeforeEach
    void setUp() {


        skillDto = SkillDto.builder()
                .id(1L)
                .name("java")
                .version("17")
                .build();
    }

    @Test
    @Order(1)
    void fetchSkills() {
        List<SkillDto> skillsDto = new ArrayList<>();
        skillsDto.add(skillDto);
        when(service.getAllSkills()).thenReturn(skillsDto);
        Response response = controller.fetchSkills();
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<SkillDto> entity = (List<SkillDto>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals(1L, entity.get(0).getId());
        assertEquals("java", entity.get(0).getName());
        assertEquals("17", entity.get(0).getVersion());
    }

    @Test
    @Order(1)
    void getById_OK(){

        when(service.getById(1L)).thenReturn(skillDto);
        Response response = controller.getById(1L);
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        SkillDto entity = (SkillDto) response.getEntity();
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("java", entity.getName());
        assertEquals("17", entity.getVersion());

    }
    @Test
    @Order(1)
    void getById_KO(){

        when(service.getById(1L)).thenReturn(null);
        Response response = controller.getById(1L);
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
    }

    @Test
    @Order(1)
    void searchByName_OK() {

        when(service.findByUniqueName("java")).thenReturn(skillDto);
        Response response = controller.searchByUniqueName("java");
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        SkillDto entity = (SkillDto) response.getEntity();
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("java", entity.getName());
        assertEquals("17", entity.getVersion());
    }

    @Test
    @Order(1)
    void searchByName_KO() {
        when(service.findByUniqueName("java")).thenReturn(null);
        Response response = controller.searchByUniqueName("java");
        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }
    @Test
    @Order(1)
    void searchByVersion_OK() {
        List<SkillDto> skillsDto = new ArrayList<>();
        skillsDto.add(skillDto);

        when(service.findByVersion("17")).thenReturn(skillsDto);
        Response response = controller.searchByVersion("17");
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        List<SkillDto> entity = (List<SkillDto>) response.getEntity();
        assertFalse(entity.isEmpty());
        assertEquals(1L, entity.get(0).getId());
        assertEquals("java", entity.get(0).getName());
        assertEquals("17", entity.get(0).getVersion());
    }

    @Test
    @Order(1)
    void searchByVersion_KO() {
        when(service.findByVersion("17")).thenReturn(null);
        Response response = controller.searchByVersion("17");
        assertNotNull(response);
        assertNull(response.getEntity());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

    }

    @Test
    @Order(2)
    void createSkill_OK() {
        SkillDto skillDto = SkillDto.builder()
                .name("new skill")
                .version("12")
                .build();
        SkillDto skillDtoCreated = SkillDto.builder()
                .id(2L)
                .name("new skill")
                .version("12")
                .build();

        when(service.createSkill(skillDto)).thenReturn(skillDtoCreated);
        Response response = controller.createSkill(skillDto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }
    @Test
    @Order(2)
    void createSkill_KO() {
        when(service.createSkill(skillDto)).thenReturn(null);
        Response response = controller.createSkill(skillDto);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    @Order(3)
    void updateSkill_OK() {
        SkillDto skillDto = SkillDto.builder()
                .name("new skill")
                .version("12")
                .build();
        SkillDto skillDtoupdated = SkillDto.builder()
                .id(2L)
                .name("new skill")
                .version("12")
                .build();

        when(service.updateSkill(1L,skillDto)).thenReturn(skillDtoupdated);
        Response response = controller.updateSkill(1L,skillDto);
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getLocation());
    }

    @Test
    @Order(3)
    void updatedSkill_KO() {
        when(service.updateSkill(1L,skillDto)).thenReturn(null);
        Response response = controller.updateSkill(1L,skillDto);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
    @Test
    @Order(4)
    void deleteSkill_OK() {
        when(service.deleteSkill(1L)).thenReturn(true);
        Response response = controller.deleteSkill(1L);
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

    }

    @Test
    @Order(4)
    void deleteSkill_KO() {
        when(service.deleteSkill(1L)).thenReturn(false);
        Response response = controller.deleteSkill(1L);
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

    }
}