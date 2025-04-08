package fr.eletutour.jasper;

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
import java.util.Map;

@Service
public class JasperService {
    private final Logger LOGGER = LoggerFactory.getLogger(JasperService.class);

    private String reportName;
    private JasperReport report;
    private JasperPrint jasperPrint;

    private Map<String, Object> parameters;


    public void compileAndFillReport() {
        compileReport();
        fillReport();
    }

    public void compileReport() {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName);
            if (reportStream == null) {
                LOGGER.error("Le fichier .jrxml n'a pas été trouvé : /reports/{}", reportName);
                return;
            }
            report = JasperCompileManager.compileReport(reportStream);
            LOGGER.info("Rapport compilé avec succès : {}", reportName);
            JRSaver.saveObject(report, reportName.replace(".jrxml", ".jasper"));
        } catch (JRException e) {
            LOGGER.error("Erreur lors de la compilation du rapport : {}", e.getMessage(), e);
        }
    }

    public void fillReport() {
        try {
            if (report == null) {
                LOGGER.error("Le rapport n'est pas compilé (report est null)");
                return;
            }
            jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            LOGGER.info("Rapport rempli avec succès");
        } catch (JRException e) {
            LOGGER.error("Erreur lors du remplissage du rapport : {}", e.getMessage(), e);
        }
    }

    public void exportReportToPDF(String fileName) {
        try {
            if (jasperPrint == null || jasperPrint.getPages().isEmpty()) {
                LOGGER.error("Le JasperPrint est vide ou non initialisé");
                return;
            }
            JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
            LOGGER.info("PDF exporté avec succès : {}", fileName);
        } catch (JRException e) {
            LOGGER.error("Erreur lors de l'exportation en PDF : {}", e.getMessage(), e);
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
