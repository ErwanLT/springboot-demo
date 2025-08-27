package fr.eletutour.ldap.service;

import fr.eletutour.ldap.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;

    public LdapService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<UserDto> findAllUsers() {
        return ldapTemplate.search(
                "ou=users",
                "(objectclass=inetOrgPerson)",
                (AttributesMapper<UserDto>) attrs -> new UserDto(
                        attrs.get("cn").get().toString(),
                        attrs.get("uid").get().toString(),
                        attrs.get("mail").get().toString()
                )
        );
    }
}
