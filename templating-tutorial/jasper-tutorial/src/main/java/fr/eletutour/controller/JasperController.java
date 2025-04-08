package fr.eletutour.controller;

import fr.eletutour.jasper.JasperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JasperController {

    private final JasperService jasperService;

    public JasperController(JasperService jasperService) {
        this.jasperService = jasperService;
    }

    @GetMapping
    public void getReport() {
        jasperService.setReportName("reportTest.jrxml");
        jasperService.compileAndFillReport();
        jasperService.exportReportToPDF("test.pdf");
    }
}
