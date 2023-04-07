package ch.qiminfo.skills.repository;

import ch.qiminfo.skills.domain.Skill;
import ch.qiminfo.skills.service.SkillService;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SkillRepository implements PanacheRepository<Skill> {
    private Logger LOGGER = Logger.getLogger(SkillRepository.class);
    public List<Skill> findByVersion(String version){
        return list("SELECT s from Skill s WHERE s.version = ?1 ORDER BY id DESC", version);
    }

    public List<Skill> findByName(String name){
        return list("SELECT s from Skill s WHERE s.name = ?1 ORDER BY id DESC", name);
    }
}
