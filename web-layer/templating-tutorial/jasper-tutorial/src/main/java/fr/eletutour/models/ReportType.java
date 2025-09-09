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
