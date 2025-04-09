package fr.eletutour.jasper;

import fr.eletutour.exception.JasperException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static fr.eletutour.exception.JasperException.JasperError.JASPER_INTERNAL_ERROR;
import static fr.eletutour.exception.JasperException.JasperError.REPORT_NOT_FOUND;

@Service
public class JasperService {
    private final Logger LOGGER = LoggerFactory.getLogger(JasperService.class);

    private String reportName;
    private JasperReport report;
    private JasperPrint jasperPrint;

    private Map<String, Object> parameters = new HashMap<>();


    public void compileAndFillReport() throws JasperException {
        compileReport();
        fillReport();
    }

    public void compileReport() throws JasperException {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName);
            if (reportStream == null) {
                LOGGER.error("Le fichier .jrxml n'a pas été trouvé : /reports/{}", reportName);
                throw new JasperException(REPORT_NOT_FOUND, "Le fichier .jrxml n'a pas été trouvé : /reports/" + reportName);
            }
            report = JasperCompileManager.compileReport(reportStream);
            LOGGER.info("Rapport compilé avec succès : {}", reportName);
            JRSaver.saveObject(report, reportName.replace(".jrxml", ".jasper"));
        } catch (JRException e) {
            LOGGER.error("Erreur lors de la compilation du rapport : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de la compilation du rapport : " + e.getMessage());
        }
    }

    public void fillReport() throws JasperException {
        try {
            if (report == null) {
                LOGGER.error("Le rapport n'est pas compilé (report est null)");
                throw new JasperException(JASPER_INTERNAL_ERROR, "Le rapport n'est pas compilé (report est null)");
            }
            parameters.put("nom", "Eletutour");
            jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            LOGGER.info("Rapport rempli avec succès");
        } catch (JRException e) {
            LOGGER.error("Erreur lors du remplissage du rapport : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors du remplissage du rapport : " + e.getMessage());
        }
    }

    public void exportReportToPDF(String fileName) throws JasperException {
        try {
            if (jasperPrint == null || jasperPrint.getPages().isEmpty()) {
                LOGGER.error("Le JasperPrint est vide ou non initialisé");
                throw new JasperException(JASPER_INTERNAL_ERROR, "Le JasperPrint est vide ou non initialisé");
            }
            JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
            LOGGER.info("PDF exporté avec succès : {}", fileName);
        } catch (JRException e) {
            LOGGER.error("Erreur lors de l'exportation en PDF : {}", e.getMessage(), e);
            throw new JasperException(JASPER_INTERNAL_ERROR, "Erreur lors de l'exportation en PDF : " + e.getMessage());
        }
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public JasperReport getReport() {
        return report;
    }

    public void setReport(JasperReport report) {
        this.report = report;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
