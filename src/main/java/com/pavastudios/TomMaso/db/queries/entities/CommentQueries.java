package com.pavastudios.TomMaso.db.queries.entities;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.Entities;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.model.Commento;
import com.pavastudios.TomMaso.model.Messaggio;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SuppressWarnings("unused")
public class CommentQueries {
    public static MasterPreparedStatement FIND_COMMENT_BY_ID;
    static MasterPreparedStatement FETCH_COMMENT_FOR_PAGE;
    static MasterPreparedStatement SEND_COMMENT;
    static MasterPreparedStatement UPDATE_BLOG_COMMENTS;
    static MasterPreparedStatement MOVE_COMMENTS;
    static MasterPreparedStatement DELETE_COMMENTS_FOR_POST;
    static MasterPreparedStatement DELETE_COMMENT;


    public static void initQueries() throws SQLException {
        FETCH_COMMENT_FOR_PAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `url_pagina`=? ORDER BY `data_invio`");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        SEND_COMMENT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Commento`(`mittente`,`testo`,`url_pagina`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        UPDATE_BLOG_COMMENTS = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Commento` SET `url_pagina`=CONCAT(?,RIGHT(`url_pagina`,LENGTH(`url_pagina`)-LENGTH(?)-2)) WHERE `url_pagina` LIKE ?");
        MOVE_COMMENTS = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Commento` SET `url_pagina`=? WHERE `url_pagina`=?");
        DELETE_COMMENTS_FOR_POST = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Commento` WHERE `url_pagina` LIKE ?");
        DELETE_COMMENT = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Commento` WHERE `id_commento` =?");
    }

    /*L'url deve essere del tipo /NOMEBLOG/... */
    public static void deleteCommentsForBlog(String postUrl) throws SQLException {
        postUrl = Queries.escapeLike(postUrl);
        postUrl += "%";//Se si sta cancellando una cartella allora tutti quelli che iniziano con uno specifico nome devono essere cancellati
        DELETE_COMMENTS_FOR_POST.setString(1, postUrl);
        DELETE_COMMENTS_FOR_POST.executeUpdate();
    }

    public static List<Commento> fetchCommentsFromPage(String page) throws SQLException {
        FETCH_COMMENT_FOR_PAGE.setString(1, page);
        ResultSet rs = FETCH_COMMENT_FOR_PAGE.executeQuery();
        List<Commento> comments = Queries.resultSetToList(Entities.COMMENTO, rs);
        rs.close();
        return comments;
    }

    public static @Nullable Commento sendComment(Utente utente, String messaggio, String pagina) throws SQLException {
        if (utente == null || messaggio == null || pagina == null) return null;
        SEND_COMMENT.setInt(1, utente.getIdUtente());
        SEND_COMMENT.setString(2, messaggio);
        SEND_COMMENT.setString(3, pagina);
        SEND_COMMENT.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(SEND_COMMENT);
        return CommentQueries.findCommentById(id);
    }

    //Query Commento
    public static @Nullable Commento findCommentById(int idCommento) throws SQLException {
        return Queries.findById(Entities.COMMENTO, idCommento);
    }

    //Query Messaggio
    public static @Nullable Messaggio findMessageById(int idMessaggio) throws SQLException {
        return Queries.findById(Entities.MESSAGGIO, idMessaggio);
    }

    public static void deleteCommento(Commento commento) throws SQLException {
        DELETE_COMMENT.setInt(1, commento.getIdCommento());
        DELETE_COMMENT.executeUpdate();
    }
}
