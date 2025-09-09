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
package fr.eletutour.service;

import fr.eletutour.exception.JasperException;
import fr.eletutour.models.JasperRequest;
import fr.eletutour.models.ReportParameter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.json.data.JsonDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.eletutour.exception.JasperException.JasperError.JASPER_INTERNAL_ERROR;
import static fr.eletutour.exception.JasperException.JasperError.REPORT_NOT_FOUND;

@Service
public class JasperService {
    private final Logger LOGGER = LoggerFactory.getLogger(JasperService.class);

    private JasperReport compileReport(String reportPath) throws JasperException {
        try (InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportPath)) {
            if (reportStream == null) {
                LOGGER.error("Le fichier .jrxml n'a pas été trouvé : /reports/{}", reportPath);
                throw new JasperException(REPORT_NOT_FOUND, "Le fichier .jrxml n'a pas été trouvé : /reports/" + reportPath);
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            LOGGER.info("Rapport compilé avec succès : {}", reportPath);
            return jasperReport;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la compilation du rapport {} : {}", reportPath, e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur compilation rapport : " + e.getMessage());
        }
    }

    public byte[] getPDF(JasperRequest request) throws JasperException {
        try {
            String mainReportPath = request.reportType().getMainReport().path();
            String directory = mainReportPath.substring(0, mainReportPath.lastIndexOf('/') + 1);

            // Compiler le rapport principal
            JasperReport mainReport = compileReport(mainReportPath);

            // Préparer les paramètres
            Map<String, Object> parameters = convertToMap(request.parameters());

            // Ajouter les sous-rapports automatiquement
            addSubReportsFromDirectory(directory, mainReportPath, parameters);

            // Gérer la source de données
            JRDataSource dataSource;
            if (!request.reportType().needDataSource()) {
                LOGGER.info("Pas de datasource nécessaire pour {}", request.reportType().getMainReport().name());
                dataSource = new JREmptyDataSource();
            } else {
                String jsonString = String.valueOf(parameters.get("jsonString"));
                InputStream jsonStream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
                dataSource = new JsonDataSource(jsonStream);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                return outputStream.toByteArray();
            }

        } catch (JRException e) {
            LOGGER.error("Erreur Jasper : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur traitement rapport : " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Erreur export PDF : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur exportation PDF : " + e.getMessage());
        }
    }

    private void addSubReportsFromDirectory(String directory, String mainReportPath, Map<String, Object> parameters) throws JasperException {
        try {
            // On liste les fichiers dans le répertoire, via le classloader (compatible JAR)
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:/reports/" + directory + "*.jrxml");

            String mainFileName = Paths.get(mainReportPath).getFileName().toString();

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename == null || filename.equals(mainFileName)) continue;

                try (InputStream stream = resource.getInputStream()) {
                    JasperReport report = JasperCompileManager.compileReport(stream);
                    String paramName = filename.replace(".jrxml", "");
                    parameters.put(paramName, report);
                    LOGGER.info("Sous-rapport compilé et ajouté au paramètre '{}'", paramName);
                }
            }
        } catch (IOException | JRException e) {
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors du chargement des sous-rapports : " + e.getMessage());
        }
    }

    public static Map<String, Object> convertToMap(List<ReportParameter> parameters) {
        Map<String, Object> result = new HashMap<>();
        if (parameters != null) {
            for (ReportParameter param : parameters) {
                result.put(param.getName(), param.getValue());
            }
        }
        return result;
    }
}
