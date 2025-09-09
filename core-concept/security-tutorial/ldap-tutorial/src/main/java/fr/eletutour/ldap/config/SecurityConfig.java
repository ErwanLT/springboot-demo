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
package fr.eletutour.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public UnboundIdContainer ldapContainer() {
        UnboundIdContainer container = new UnboundIdContainer("dc=example,dc=com", "classpath:users.ldif");
        container.setPort(0); // Use a random available port
        return container;
    }

    @Bean
    public LdapContextSource contextSource(UnboundIdContainer container) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:" + container.getPort());
        contextSource.setUserDn("cn=admin,dc=example,dc=com");
        contextSource.setPassword("admin-password");
        contextSource.setBase("dc=example,dc=com");
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(LdapContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserSearchFilter("(uid={0})");
        factory.setUserSearchBase("ou=users");

        DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups");
        authoritiesPopulator.setGroupSearchFilter("uniqueMember={0}");
        factory.setLdapAuthoritiesPopulator(authoritiesPopulator);

        return factory.createAuthenticationManager();
    }
}