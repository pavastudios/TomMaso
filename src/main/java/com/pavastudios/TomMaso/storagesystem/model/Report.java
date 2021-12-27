package com.pavastudios.TomMaso.storagesystem.model;

import com.google.gson.stream.JsonWriter;
import com.pavastudios.TomMaso.accesssystem.UserQueries;
import com.pavastudios.TomMaso.blogsystem.blogvisualization.CommentQueries;
import com.pavastudios.TomMaso.chatsystem.ChatQueries;
import com.pavastudios.TomMaso.storagesystem.FileUtility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Classe che modella il concetto di Report nel database
 */
public class Report implements GenericModel {

    private int idReport;
    private Type type;
    private String url;
    private String reason;
    private Utente reporter;
    private Date reportDate;
    private Status reportStatus;

    public static Report fromResultSet(ResultSet rs) throws SQLException {
        Report p = new Report();
        p.setIdReport(rs.getInt("id_report"));
        p.setType(Type.values()[rs.getInt("tipo")]);
        p.setReportStatus(Status.values()[rs.getInt("status")]);
        p.setUrl(rs.getString("url"));
        p.setReason(rs.getString("motivo"));
        p.setReporter(UserQueries.findUserById(rs.getInt("reporter")));
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

    public Status getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Status reportStatus) {
        this.reportStatus = reportStatus;
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
        writer.name("status").value(reportStatus.ordinal());
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
                ", status=" + reportStatus +
                ", url='" + url + '\'' +
                ", reason='" + reason + '\'' +
                ", reporter=" + reporter +
                ", reportDate=" + reportDate +
                '}';
    }

    /**
     * Metodo che cancella dal database il report in questione
     * @throws SQLException Problemi con l'eliminazione delle informazioni dal database
     */
    public void deleteContent() throws SQLException {
        switch (this.getType()) {
            case COMMENT:
                Commento commento = CommentQueries.findCommentById(Integer.parseInt(this.getUrl().split("-")[1]));
                assert commento != null;
                CommentQueries.deleteCommento(commento);
                break;
            case CHAT:
                Messaggio messaggio = ChatQueries.findMessageById(Integer.parseInt(this.getUrl().split("-")[1]));
                assert messaggio != null;
                ChatQueries.deleteMessage(messaggio);
                break;
            case POST:
                String url = this.getUrl().substring(6);
                File file = FileUtility.blogPathToFile(url);
                FileUtility.recursiveDelete(file);
                CommentQueries.deleteCommentsForBlog(url);
                break;
        }
    }

    /**
     * Enumerazione per identificare il tipo di report
     */
    public enum Type {COMMENT, CHAT, POST}

    /**
     * Enumerazione per identificare lo stato in cui si trova il report
     */
    public enum Status {NOT_REVIEWED, ACCEPTED, REJECTED}
}
