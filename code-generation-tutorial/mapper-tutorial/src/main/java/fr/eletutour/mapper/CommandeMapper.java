package fr.eletutour.mapper;

import fr.eletutour.dto.CommandeDTO;
import fr.eletutour.model.Commande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface CommandeMapper {
    @Mapping(target = "dateFormatee", source = "dateCreation", qualifiedByName = "formatDate")
    CommandeDTO toCommandeDTO(Commande commande);

    @Named("formatDate")
    default String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}