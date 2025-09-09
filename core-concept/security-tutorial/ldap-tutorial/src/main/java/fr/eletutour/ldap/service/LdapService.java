/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of ldap-tutorial.
 *
 * ldap-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ldap-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ldap-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.ldap.service;

import fr.eletutour.ldap.dto.NewUserDto;
import fr.eletutour.ldap.dto.UserDto;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapEncoder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
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
                (ContextMapper<UserDto>) ctx -> {
                    DirContextAdapter adapter = (DirContextAdapter) ctx;
                    String userDn = adapter.getNameInNamespace();
                    List<String> roles = findUserRoles(userDn);
                    return new UserDto(
                            adapter.getStringAttribute("cn"),
                            adapter.getStringAttribute("uid"),
                            adapter.getStringAttribute("mail"),
                            roles
                    );
                }
        );
    }

    private List<String> findUserRoles(String userDn) {
        return ldapTemplate.search(
                "ou=groups",
                "(&(objectclass=groupOfUniqueNames)(uniqueMember=" + LdapEncoder.filterEncode(userDn) + "))",
                (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get()
        );
    }

    public void createUser(NewUserDto userDto) {
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", "users")
                .add("cn", userDto.getFullName())
                .build();

        DirContextAdapter context = new DirContextAdapter(dn);
        context.setAttributeValues("objectclass", new String[]{"top", "inetOrgPerson"});
        context.setAttributeValue("cn", userDto.getFullName());
        String[] names = userDto.getFullName().split(" ");
        context.setAttributeValue("sn", names.length > 1 ? names[names.length - 1] : userDto.getFullName());
        context.setAttributeValue("uid", userDto.getUid());
        context.setAttributeValue("mail", userDto.getEmail());
        context.setAttributeValue("userPassword", userDto.getPassword());

        ldapTemplate.bind(context);
    }

    public void addUserToGroup(String userFullName, String groupName) {
        Name userDn = LdapNameBuilder.newInstance("dc=example,dc=com")
                .add("ou", "users")
                .add("cn", userFullName)
                .build();

        Name groupDn = LdapNameBuilder.newInstance()
                .add("ou", "groups")
                .add("cn", groupName)
                .build();

        DirContextOperations group = ldapTemplate.lookupContext(groupDn);
        group.addAttributeValue("uniqueMember", userDn.toString());
        ldapTemplate.modifyAttributes(group);
    }
}