package ch.qiminfo.skills.mapper;

import ch.qiminfo.skills.domain.Skill;
import ch.qiminfo.skills.dto.SkillDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillMapper {

    // --- Skill to DTO

    SkillDto toDto(Skill skill);

    // --- DTO to Skill
    @Mapping(target = "id", ignore = true)
    Skill toEntity(SkillDto skillDto);
}
