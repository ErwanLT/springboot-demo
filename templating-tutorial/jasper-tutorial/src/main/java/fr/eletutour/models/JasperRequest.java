package fr.eletutour.models;

import java.util.List;

public class JasperRequest {
    private String exportName;
    private ReportType reportType; // Remplace String reportName
    private List<ReportParameter> parameters;

    public JasperRequest(String exportName, ReportType reportType, List<ReportParameter> parameters) {
        this.exportName = exportName;
        this.reportType = reportType;
        this.parameters = parameters;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public List<ReportParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ReportParameter> parameters) {
        this.parameters = parameters;
    }

    public String getExportName() {
        return exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }
}