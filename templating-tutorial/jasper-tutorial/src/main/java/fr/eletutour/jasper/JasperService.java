package fr.eletutour.jasper;

import fr.eletutour.exception.JasperException;
import fr.eletutour.models.JasperRequest;
import fr.eletutour.models.ReportParameter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            LOGGER.info("Rapport compilé avec succès : {}", reportFileName);
            return jasperReport;
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la compilation du rapport {} : {}", reportFileName, e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de la compilation du rapport : " + e.getMessage());
        }
    }

    public byte[] getPDF(JasperRequest request) throws JasperException {
        // Compiler le rapport principal
        JasperReport mainReport = compileReport(request.getReportType().getMainReportName());

        // Préparer les paramètres avec les sous-rapports
        Map<String, Object> parameters = convertToMap(request.getParameters());
        for (String subReportName : request.getReportType().getSubReportNames()) {
            JasperReport subReport = compileReport(subReportName);
            parameters.put(subReportName.replace(".jrxml", ""), subReport); // Ex. "subReport"
        }
        LOGGER.info("Paramètres avant remplissage : {}", parameters);

        // Remplir et exporter en une seule passe
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, new JREmptyDataSource());
            LOGGER.info("Rapport rempli avec succès");
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            LOGGER.info("PDF exporté avec succès : {}", request.getExportName());
            return outputStream.toByteArray();
        } catch (JRException e) {
            LOGGER.error("Erreur lors du traitement du rapport : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors du traitement du rapport : " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'exportation en PDF : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de l'exportation en PDF : " + e.getMessage());
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
