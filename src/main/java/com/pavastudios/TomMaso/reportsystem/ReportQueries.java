package com.pavastudios.TomMaso.reportsystem;

import com.pavastudios.TomMaso.storagesystem.Entities;
import com.pavastudios.TomMaso.storagesystem.GlobalConnection;
import com.pavastudios.TomMaso.storagesystem.MasterPreparedStatement;
import com.pavastudios.TomMaso.storagesystem.Queries;
import com.pavastudios.TomMaso.storagesystem.model.Report;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * La classe ReportQueries contiene i metodi per l'interazione con il database relativi ai Report
 */
public class ReportQueries {

    public static MasterPreparedStatement FIND_REPORT_BY_ID;
    static MasterPreparedStatement CREATE_REPORT;
    static MasterPreparedStatement FETCH_FROM_TYPE;
    static MasterPreparedStatement REVIEW_REPORT;

    /**
     * Inizializza le prepared statements contenenti le query relative ai commenti
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        FIND_REPORT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Report` WHERE `id_report`=?", Statement.RETURN_GENERATED_KEYS);
        CREATE_REPORT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Report`(`tipo`,`url`,`motivo`,`reporter`,`target`) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        FETCH_FROM_TYPE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Report` WHERE `status`=0 AND `tipo` = ?");
        REVIEW_REPORT = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Report` SET `status`=? WHERE `id_report`=?");
    }

    /**
     * Esegue la query che crea un report
     * @param comment Tipo di segnalazione
     * @param user Utente che effettua la segnalazione
     * @param url Url del contenuto della segnalazione
     * @param reason Motivazione della segnalazione
     * @param target Utente segnalato
     * @return Report creato
     * @throws SQLException Problemi con il database
     */
    public static @NotNull Report report(@NotNull Report.Type comment, @NotNull Utente user, @NotNull String url, @NotNull String reason, @Nullable Utente target) throws SQLException {
        CREATE_REPORT.setInt(1, comment.ordinal());
        CREATE_REPORT.setString(2, url);
        CREATE_REPORT.setString(3, reason);
        CREATE_REPORT.setInt(4, user.getIdUtente());
        CREATE_REPORT.setInt(5, target.getIdUtente());
        CREATE_REPORT.executeUpdate();
        int idReport = Utility.getIdFromGeneratedKeys(CREATE_REPORT);
        return Objects.requireNonNull(findReportById(idReport));
    }

    /**
     * Esegue la query che recupera un report da id
     * @param idReport Id della segnalazione
     * @return Report trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Report findReportById(int idReport) throws SQLException {
        return Queries.findById(Entities.REPORT, idReport);
    }

    /**
     * Esegue la query che recupera la lista di segnalazione non controllate di un determinato tipo
     * @param type Tipo della segnalazione
     * @return Lista delle segnalazioni trovate
     * @throws SQLException Problemi con il database
     */
    public static List<Report> fetchUnreviewedOfType(Report.Type type) throws SQLException {
        FETCH_FROM_TYPE.setInt(1, type.ordinal());
        ResultSet rs = FETCH_FROM_TYPE.executeQuery();
        List<Report> reports = Queries.resultSetToList(Entities.REPORT, rs);
        rs.close();
        return reports;
    }

    /**
     * Esegue la query che memorizza l'esito di un report
     * @param report Report analizzato
     * @param approved Esito del report
     * @throws SQLException Problemi con il database
     */
    public static void reviewReport(Report report, Report.Status approved) throws SQLException {
        REVIEW_REPORT.setInt(1, approved.ordinal());
        REVIEW_REPORT.setInt(2, report.getIdReport());
        REVIEW_REPORT.executeUpdate();
    }
}
