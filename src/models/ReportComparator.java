package models;

import java.util.Comparator;

public class ReportComparator implements Comparator<Report> {

    @Override
    public int compare(Report r1, Report r2) {
        return r1.getId() < r2.getId() ? 1 : -1;
    }
}
