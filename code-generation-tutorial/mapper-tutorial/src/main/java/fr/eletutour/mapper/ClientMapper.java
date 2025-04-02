package fr.eletutour.mapper;

import fr.eletutour.dto.ClientDTO;
import fr.eletutour.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AdresseMapper.class, CommandeMapper.class})
public interface ClientMapper {
    @Mapping(target = "nomComplet", expression = "java(client.getNom() + \" \" + client.getPrenom())")
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}
