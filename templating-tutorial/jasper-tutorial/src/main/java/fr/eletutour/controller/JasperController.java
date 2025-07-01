package fr.eletutour.controller;

import fr.eletutour.exception.JasperException;
import fr.eletutour.jasper.JasperService;
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
