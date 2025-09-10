/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of client.
 *
 * client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with client.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class ArticlesController {

    private final WebClient webClient;

    public ArticlesController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/articles")
    public String[] getArticles(
      @RegisteredOAuth2AuthorizedClient("articles-client-oidc") OAuth2AuthorizedClient authorizedClient
    ) {
        return this.webClient
          .get()
          .uri("http://127.0.0.1:8090/articles")
          .attributes(oauth2AuthorizedClient(authorizedClient))
          .retrieve()
          .bodyToMono(String[].class)
          .block();
    }
}