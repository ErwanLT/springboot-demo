/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of mapper-tutorial.
 *
 * mapper-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mapper-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mapper-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
