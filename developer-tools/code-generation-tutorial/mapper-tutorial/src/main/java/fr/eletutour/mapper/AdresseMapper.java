package fr.eletutour.mapper;

import fr.eletutour.dto.AdresseDTO;
import fr.eletutour.model.Adresse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdresseMapper {
    AdresseMapper INSTANCE = Mappers.getMapper(AdresseMapper.class);
    AdresseDTO toDto(Adresse adresse);
    Adresse toEntity(AdresseDTO adresseDTO);
}
