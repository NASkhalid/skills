package ch.qiminfo.skills.controller;

import ch.qiminfo.skills.dto.SkillDto;
import ch.qiminfo.skills.repository.SkillRepository;
import ch.qiminfo.skills.service.SkillService;
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

@Path("/api/skills")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SkillController {
    private Logger LOGGER = Logger.getLogger(SkillController.class);
    @Inject
    SkillService service;

    @GET
    @PermitAll
    public Response fetchSkills() {
        LOGGER.info("Get all skills from the database");
        List<SkillDto> skillsDto = service.getAllSkills();
        return Response.ok(skillsDto).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response getById(@PathParam("id") Long id) {
        LOGGER.info("Get  skill from the database by ID");
        SkillDto skillDto = service.getById(id);
        if (skillDto != null)
            return Response.ok(skillDto).build();

        return Response.status(NOT_FOUND).build();
    }

    @GET
    @Path("/name/unique/{name}")
    public Response searchByUniqueName(@PathParam("name") String name) {
        LOGGER.debug("Get ONE skill from the database by NAME");
        SkillDto skillDto = service.findByUniqueName(name);
        if (skillDto != null)
            return Response.ok(skillDto).build();

        return Response.status(NOT_FOUND).build();
    }

    @GET
    @Path("/name/{name}")
    public Response searchByName(@PathParam("name") String name) {
        LOGGER.info("Get all skills from the database by NAME");
        List<SkillDto> skillsDto = service.findByName(name);
        if (skillsDto != null && !skillsDto.isEmpty())
            return Response.ok(skillsDto).build();

        return Response.status(NOT_FOUND).build();
    }

    @GET
    @Path("/version/{version}")
    public Response searchByVersion(@PathParam("version") String version) {
        LOGGER.info("Get skill from the database by VERSION");
        List<SkillDto> skillsDto = service.findByVersion(version);
        if (skillsDto != null && !skillsDto.isEmpty())
            return Response.ok(skillsDto).build();

        return Response.status(NOT_FOUND).build();
    }

    @POST
    //@RolesAllowed({"admin","user"})
    @PermitAll
    public Response createSkill(SkillDto skilldto) {
        LOGGER.info("Create skill inside database");
        SkillDto skillDtoCreated = service.createSkill(skilldto);
        if (skillDtoCreated != null) {
            return Response.created(URI.create("/skills/" + skillDtoCreated.getId())).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    @PUT
    @PermitAll
    @Path("/{id}")
    public Response updateSkill(@PathParam("id") Long id, SkillDto skillDto){
        LOGGER.info("Update skill from the database");
        SkillDto skillDtoupdated = service.updateSkill(id,skillDto);
        if (skillDtoupdated != null) {
            return Response.created(URI.create("/skills/" + skillDto.getId())).build();
        }
        return Response.status(BAD_REQUEST).build();

    }

    @DELETE
    @Path("/{id}")
    //@RolesAllowed("admin")
    @PermitAll
    public Response deleteSkill(@PathParam("id") Long id) {
        LOGGER.info("Delete skill from the database by ID");
        boolean deleted = service.deleteSkill(id);
        return deleted ? Response.noContent().build() : Response.status(BAD_REQUEST).build();
    }

}
