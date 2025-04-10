package fr.eletutour.models;

import java.util.Collections;
import java.util.List;

public enum ReportType {
    // Exemple de rapports avec leurs sous-rapports
    SIMPLE_REPORT("simpleReport.jrxml", Collections.emptyList()),
    COMPLEX_REPORT("reportTest.jrxml", List.of("subReport.jrxml"));

    private final String mainReportName;
    private final List<String> subReportNames;

    ReportType(String mainReportName, List<String> subReportNames) {
        this.mainReportName = mainReportName;
        this.subReportNames = subReportNames;
    }

    public String getMainReportName() {
        return mainReportName;
    }

    public List<String> getSubReportNames() {
        return subReportNames;
    }
}