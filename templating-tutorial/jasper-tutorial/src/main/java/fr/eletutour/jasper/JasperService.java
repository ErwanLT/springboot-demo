package fr.eletutour.jasper;

import fr.eletutour.exception.JasperException;
import fr.eletutour.models.JasperRequest;
import fr.eletutour.models.ReportParameter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.eletutour.exception.JasperException.JasperError.JASPER_INTERNAL_ERROR;
import static fr.eletutour.exception.JasperException.JasperError.REPORT_NOT_FOUND;

@Service
public class JasperService {
    private final Logger LOGGER = LoggerFactory.getLogger(JasperService.class);

    private JasperReport compileReport(String reportFileName) throws JasperException {
        try (InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportFileName)) {
            if (reportStream == null) {
                LOGGER.error("Le fichier .jrxml n'a pas été trouvé : /reports/{}", reportFileName);
                throw new JasperException(REPORT_NOT_FOUND, "Le fichier .jrxml n'a pas été trouvé : /reports/" + reportFileName);
            }
            // Compile le JRXML en JasperReport en mémoire (pas de fichier .jasper généré)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            LOGGER.info("Rapport compilé avec succès : {}", reportFileName);
            return jasperReport;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la compilation du rapport {} : {}", reportFileName, e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de la compilation du rapport : " + e.getMessage());
        }
    }

    /**
     * Génère un PDF à partir d'un JasperRequest.
     * Compile les rapports (principal + sous-rapports), sauvegarde les jasper, puis remplit le rapport principal.
     * @param request demande contenant les paramètres et le type de rapport
     * @return byte[] PDF généré
     * @throws JasperException en cas d'erreur
     */
    public byte[] getPDF(JasperRequest request) throws JasperException {
        try {
            // Compiler rapport principal
            JasperReport mainReport = compileReport(request.reportType().getMainReportName());

            // Compiler sous-rapports en mémoire et les ajouter aux paramètres
            Map<String, Object> parameters = convertToMap(request.parameters());
            for (String subReportName : request.reportType().getSubReportNames()) {
                JasperReport subReport = compileReport(subReportName);
                // Nom du paramètre = nom fichier sans extension, ex: "subReport"
                String paramName = subReportName.replace(".jrxml", "");
                parameters.put(paramName, subReport);
            }

            // Data source JSON
            InputStream jsonStream = new ByteArrayInputStream(parameters.get("jsonString").toString().getBytes(StandardCharsets.UTF_8));
            JsonDataSource dataSource = new JsonDataSource(jsonStream);

            LOGGER.info("paramètres : {}", parameters);

            // Remplir le rapport principal, sous-rapports en paramètre
            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                return outputStream.toByteArray();
            }
        } catch (JRException e) {
            LOGGER.error("Erreur rapport : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors du traitement du rapport : " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Erreur export PDF : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de l'exportation en PDF : " + e.getMessage());
        }
    }

    /**
     * Convertit une liste de ReportParameter en Map<String,Object>
     * @param parameters liste des paramètres
     * @return Map nom -> valeur
     */
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
