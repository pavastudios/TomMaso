package com.pavastudios.TomMaso.db.queries.entities;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Report;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ReportQueries {

    public static MasterPreparedStatement FIND_REPORT_BY_ID;
    static MasterPreparedStatement CREATE_REPORT;

    public static void initQueries() throws SQLException {
        CREATE_REPORT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Report`(`tipo`,`url`,`motivo`,`reporter`,`target`) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
    }


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

    //Query Blog
    public static @Nullable Report findReportById(int idReport) throws SQLException {
        return Queries.findById(Entities.REPORT, idReport);
    }
}
