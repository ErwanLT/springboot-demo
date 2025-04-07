package fr.eletutour.mapper;


import fr.eletutour.dto.AdresseDTO;
import fr.eletutour.dto.ClientDTO;
import fr.eletutour.dto.CommandeDTO;
import fr.eletutour.model.Adresse;
import fr.eletutour.model.Client;
import fr.eletutour.model.Commande;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ClientMapperTest {

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Test
    void testClientMapper_toDTO() {
        Adresse adresse = new Adresse("1 rue de la paix", "75000", "Paris", "France");
        Commande commande = new Commande("123456", new Date());
        Client client = new Client("Dupont", "Jean", adresse, commande);

        ClientDTO clientDTO = clientMapper.toDto(client);
        assertNotNull(clientDTO, "L'objet ClientDTO ne doit pas être nul");
        assertEquals("DUPONT JEAN", clientDTO.getNomComplet());
        assertNotNull(clientDTO.getAdresse(), "L'adresse ne doit pas être nulle");
        assertNotNull(clientDTO.getCommande(), "La commande ne doit pas être nulle");
    }

    @Test
    void testClientMapper_toEntity() {
        AdresseDTO adresseDTO = new AdresseDTO("1 rue de la paix", "75000", "Paris", "France");
        CommandeDTO commandeDTO = new CommandeDTO("123456", "2025-04-07");
        ClientDTO clientDTO = new ClientDTO("Dupont", "Jean", "DUPONT JEAN", adresseDTO, commandeDTO);

        Client client = clientMapper.toEntity(clientDTO);
        assertNotNull(client, "L'objet Client ne doit pas être nul");
        assertEquals("Dupont", client.getNom());
        assertEquals("Jean", client.getPrenom());
        assertNotNull(client.getAdresse(), "L'adresse ne doit pas être nulle");
        assertNotNull(client.getCommande(), "La commande ne doit pas être nulle");
    }
}
