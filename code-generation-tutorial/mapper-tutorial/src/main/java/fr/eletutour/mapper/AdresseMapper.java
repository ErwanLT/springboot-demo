package fr.eletutour.mapper;

import fr.eletutour.dto.AdresseDTO;
import fr.eletutour.model.Adresse;
import org.mapstruct.Mapper;

@Mapper
public interface AdresseMapper {
    AdresseDTO toDto(Adresse adresse);
    Adresse toEntity(AdresseDTO adresseDTO);
}
