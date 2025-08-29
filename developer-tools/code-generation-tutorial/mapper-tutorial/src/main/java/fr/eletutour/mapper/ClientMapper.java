package fr.eletutour.mapper;

import fr.eletutour.dto.ClientDTO;
import fr.eletutour.model.Client;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AdresseMapper.class, CommandeMapper.class})
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "nomComplet", expression = "java(client.getNom() + \" \" + client.getPrenom())")
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);

    @AfterMapping
    default void enrichierDTO(@MappingTarget ClientDTO clientDTO) {
        clientDTO.setNomComplet(clientDTO.getNomComplet().toUpperCase());
    }
}
