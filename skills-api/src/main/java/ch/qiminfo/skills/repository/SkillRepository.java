package ch.qiminfo.skills.repository;

import ch.qiminfo.skills.domain.Skill;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SkillRepository implements PanacheRepository<Skill> {

    public List<Skill> findByVersion(String version){
        return list("SELECT s from Skill s WHERE s.version = ?1 ORDER BY id DESC", version);
    }

    public List<Skill> findByName(String name){
        return list("SELECT s from Skill s WHERE s.name = ?1 ORDER BY id DESC", name);
    }
}
