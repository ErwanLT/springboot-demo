package fr.eletutour.models;

import java.util.Collections;
import java.util.List;

public enum ReportType {

    SIMPLE_REPORT(
            new Report("Simple Report", "simpleReport/simpleReport.jrxml"),
            Collections.emptyList(),
            Boolean.FALSE
    ),
    REPORT_WITH_SUBREPORTS(
            new Report("Report with Subreports", "reportTest/reportTest.jrxml"),
            List.of(
                    new Report("subReport.jrxml", "subReport.jrxml")
            ),
            Boolean.FALSE
    ),
    REPORT_WITH_DATA_SOURCE(
            new Report("Report with Data Source", "testWithDatasource/testWithDatasource.jrxml"),
            Collections.emptyList(),
            Boolean.TRUE
    );


    private final Report mainReport;
    private final List<Report> subReports;
    private final Boolean needDataSource;

    ReportType(Report mainReport, List<Report> subReports, Boolean needDataSource) {
        this.mainReport = mainReport;
        this.subReports = subReports;
        this.needDataSource = needDataSource;
    }

    public Report getMainReport() {
        return mainReport;
    }

    public List<Report> getSubReports() {
        return subReports;
    }

    public Boolean needDataSource() {
        return needDataSource;
    }
}
