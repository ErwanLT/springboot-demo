package fr.eletutour.models;

import java.util.List;

public record JasperRequest(String exportName, ReportType reportType, List<ReportParameter> parameters){
}