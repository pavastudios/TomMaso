package com.pavastudios.TomMaso.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.db.queries.Queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Report implements GenericModel {

    private int idReport;
    private Type type;
    private String url;
    private String reason;
    private Utente reporter;
    private Date reportDate;

    public static Report fromResultSet(ResultSet rs) throws SQLException {
        Report p = new Report();
        p.setIdReport(rs.getInt("id_report"));
        p.setType(Type.values()[rs.getInt("tipo")]);
        p.setUrl(rs.getString("url"));
        p.setReason(rs.getString("motivo"));
        p.setReporter(Queries.findUserById(rs.getInt("reporter")));
        p.setReportDate(rs.getTimestamp("data_report"));
        return p;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Utente getReporter() {
        return reporter;
    }

    public void setReporter(Utente reporter) {
        this.reporter = reporter;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;

        return idReport == report.idReport;
    }

    @Override
    public int hashCode() {
        return idReport;
    }

    @Override
    public void writeJson(JsonWriter writer) throws IOException {
        writer.beginObject();
        writer.name("id").value(idReport);
        writer.name("type").value(type.ordinal());
        writer.name("url").value(url);
        writer.name("reason").value(reason);
        writer.name("reporter");
        reporter.writeJson(writer);
        writer.name("report-date").value(reportDate.getTime());
        writer.endObject();
    }

    @Override
    public String toString() {
        return "Report{" +
                "idReport=" + idReport +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", reason='" + reason + '\'' +
                ", reporter=" + reporter +
                ", reportDate=" + reportDate +
                '}';
    }

    public enum Type {COMMENT, CHAT, POST}
}
