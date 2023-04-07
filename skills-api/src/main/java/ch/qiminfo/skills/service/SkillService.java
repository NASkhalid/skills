package ch.qiminfo.skills.service;

import ch.qiminfo.skills.domain.Skill;
import ch.qiminfo.skills.dto.SkillDto;
import ch.qiminfo.skills.mapper.SkillMapper;
import ch.qiminfo.skills.repository.SkillRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SkillService {
    private Logger LOGGER = Logger.getLogger(SkillService.class);
    @Inject
    SkillRepository repository;
    @Inject
    SkillMapper mapper;


    public List<SkillDto> getAllSkills() {
        List<Skill> skills = repository.listAll();
        List<SkillDto> skillsDto = skills
                .stream()
                .map( skill -> mapper.toDto(skill))
                .collect(Collectors.toList());

        return skillsDto;
    }

    @Transactional
    public SkillDto createSkill(SkillDto skillDto) {
        Skill skill = mapper.toEntity(skillDto);
        repository.persist(skill);
        if(repository.isPersistent(skill)){
            return mapper.toDto(skill);
        }
        return null;
    }

    @Transactional
    public boolean deleteSkill(Long id) {
        boolean deleted = repository.deleteById(id);
        return deleted;
    }

    public List<SkillDto> findByVersion(String version) {
        List<Skill> skills = repository.findByVersion(version);
        List<SkillDto> skillsDto = skills
                .stream()
                .map( skill -> mapper.toDto(skill))
                .collect(Collectors.toList());
        return skillsDto;
    }

    public SkillDto getById(Long id) {
        return repository
                .findByIdOptional(id)
                .map(skill -> mapper.toDto(skill))
                .orElse(null);
    }

    public SkillDto findByUniqueName(String name) {
        return repository
                .find("name", name)
                .singleResultOptional()
                .map(skill -> mapper.toDto(skill))
                .orElse(null);
    }

    public List<SkillDto> findByName(String name) {
        List<Skill> skills = repository.findByName(name);
        List<SkillDto> skillsDto = skills
                .stream()
                .map( skill -> mapper.toDto(skill))
                .collect(Collectors.toList());
        return skillsDto;
    }

    @Transactional
    public SkillDto updateSkill(Long id, SkillDto skillDto) {
        return repository.findByIdOptional(id)
                .map(skill -> {
                    skill.setName(skillDto.getName());
                    skill.setVersion(skillDto.getVersion());
                    return mapper.toDto(skill);
                })
                .orElse(null);
    }
}
