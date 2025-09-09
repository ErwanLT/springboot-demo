/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jasper-tutorial.
 *
 * jasper-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jasper-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jasper-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.exception.JasperException;
import fr.eletutour.service.JasperService;
import fr.eletutour.models.JasperRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JasperController {

    private final JasperService jasperService;

    public JasperController(JasperService jasperService) {
        this.jasperService = jasperService;
    }

    @PostMapping
    public ResponseEntity<byte[]> getReport(@RequestBody JasperRequest request) throws JasperException {

        byte[] pdf = jasperService.getPDF(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(request.exportName()).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}
