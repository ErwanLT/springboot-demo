package fr.eletutour.models;

import java.util.Collections;
import java.util.List;

public enum ReportType {
    // RAPPORT MAGI
    POUVOIR(new Report("pouvoir_.jrxml", "./MAGI/PERSONNES/pouvoir/pouvoir_.jrxml"), Collections.emptyList()),
    FICHE_PERSO(new Report("fiche_personnelle_.jrxml", "./MAGI/PERSONNES/fiche_personnelle/fiche_personnelle_.jrxml"),
            List.of(new Report("add_personnelle.jrxml", "./MAGI/PERSONNES/fiche_personnelle/add_personnelle.jrxml"),
                    new Report("add_professionnelle.jrxml", "./MAGI/PERSONNES/fiche_personnelle/add_professionnelle.jrxml"),
                    new Report("infos_generales.jrxml", "./MAGI/PERSONNES/fiche_personnelle/infos_generales.jrxml"),
                    new Report("syndicat.jrxml", "./MAGI/PERSONNES/fiche_personnelle/syndicat.jrxml"))),
    //RAPPORT ESPACECO
    CCN52(new Report("CCN52.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_General_CCN52_.jrxml"),
            List.of(new Report("Agrica_CCN52_Wrapper.jrxml", "./ESPACECO/CCN52/Agrica_CCN52_Wrapper.jrxml"),
                    new Report("Agrica_Rapport_CCN52_CotisationsEstimees.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_CotisationsEstimees.jrxml"),
                    new Report("Agrica_Rapport_CCN52_Exemple_Remboursement.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_Exemple_Remboursement.jrxml"),
                    new Report("Agrica_Rapport_CCN52_InformationFraisGestion.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_InformationFraisGestion.jrxml"),
                    new Report("Agrica_Rapport_CCN52_InformationPrecontractuelle.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_InformationPrecontractuelle.jrxml"),
                    new Report("Agrica_Rapport_CCN52_Informations.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_Informations.jrxml"),
                    new Report("Agrica_Rapport_CCN52_IPID.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_IPID.jrxml"),
                    new Report("Agrica_Rapport_CCN52_Prestations.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_Prestations.jrxml"),
                    new Report("Agrica_Rapport_CCN52_Tableau_Garantie.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_CCN52_Tableau_Garantie.jrxml"),
                    new Report("Agrica_Rapport_Mutu_BA.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_Mutu_BA.jrxml"),
                    new Report("Agrica_Rapport_Mutu_Devoir_Conseil.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_Mutu_Devoir_Conseil.jrxml"),
                    new Report("Agrica_Rapport_Offres.jrxml", "./ESPACECO/CCN52/Agrica_Rapport_Offres.jrxml")))
    ;


    private final Report mainReport;
    private final List<Report> subReports;

    ReportType(Report mainReport, List<Report> subReports) {
        this.mainReport = mainReport;
        this.subReports = subReports;
    }

    public Report getMainReport() {
        return mainReport;
    }

    public List<Report> getSubReports() {
        return subReports;
    }
}
