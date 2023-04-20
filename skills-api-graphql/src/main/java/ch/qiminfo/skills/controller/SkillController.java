package ch.qiminfo.skills.controller;

import ch.qiminfo.skills.dto.SkillDto;
import ch.qiminfo.skills.repository.SkillRepository;
import ch.qiminfo.skills.service.SkillService;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.eclipse.microprofile.graphql.*;
import org.jboss.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@GraphQLApi
@ApplicationScoped
public class SkillController {
    private Logger LOGGER = Logger.getLogger(SkillController.class);
    private final BroadcastProcessor<SkillDto> processor = BroadcastProcessor.create();
    @Inject
    SkillService service;

    @Query("allSkills")
    @Description("Get all skills from the database")
    @PermitAll
    public List<SkillDto>  fetchSkills() {
        LOGGER.info("Get all skills from the database");
        List<SkillDto> skillsDto = service.getAllSkills();
        return skillsDto;
    }

    @Query("skillById")
    @Description("et  skill from the database by ID")
    @PermitAll
    public SkillDto getById(@Name("skillId") Long id) {
        LOGGER.info("Get  skill from the database by ID");
        SkillDto skillDto = service.getById(id);
        return skillDto;
    }

    @Query
    @Description("Get ONE skill from the database by NAME")
    public SkillDto searchByUniqueName(@Name("skillName") String name) {
        LOGGER.debug("Get ONE skill from the database by NAME");
        SkillDto skillDto = service.findByUniqueName(name);
        return skillDto;
    }

    @Query
    @Description("Get ONE skill from the database by NAME")
    public List<SkillDto> searchByName(@Name("name") String name) {
        LOGGER.info("Get all skills from the database by NAME");
        List<SkillDto> skillsDto = service.findByName(name);
        return skillsDto;
    }

    @Query
    @Description("Get skill from the database by VERSION")
    public List<SkillDto> searchByVersion(@Name("version") String version) {
        LOGGER.info("Get skill from the database by VERSION");
        List<SkillDto> skillsDto = service.findByVersion(version);
        return skillsDto;
    }

    @Mutation
    @Description("Create skill inside database")
    //@RolesAllowed({"admin","user"})
    @PermitAll
    public SkillDto createSkill(SkillDto skilldto) {
        LOGGER.info("Create skill inside database");
        SkillDto skillDtoCreated = service.createSkill(skilldto);
        processor.onNext(skillDtoCreated);
        return skillDtoCreated;
    }

    @Mutation
    @Description("Update skill from the database")
    @PermitAll
    public SkillDto updateSkill(@Name("skillId") Long id, SkillDto skillDto){
        LOGGER.info("Update skill from the database");
        SkillDto skillDtoupdated = service.updateSkill(id,skillDto);
        processor.onNext(skillDtoupdated);
        return skillDtoupdated;

    }

    @Mutation
    @Description("Delete skill from the database by ID")
    //@RolesAllowed("admin")
    @PermitAll
    public boolean deleteSkill(@Name("skillId") Long id) {
        LOGGER.info("Delete skill from the database by ID");
        boolean deleted = service.deleteSkill(id);
        return deleted;
    }

    @Subscription
    public Multi<SkillDto> skillCreated() {
        return processor;
    }

    @Subscription
    public Multi<SkillDto> skillUpdated() {
        return processor;
    }
}
