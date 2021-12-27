package com.pavastudios.TomMaso.blogsystem.blogvisualization;

import com.pavastudios.TomMaso.storagesystem.Entities;
import com.pavastudios.TomMaso.storagesystem.GlobalConnection;
import com.pavastudios.TomMaso.storagesystem.MasterPreparedStatement;
import com.pavastudios.TomMaso.storagesystem.Queries;
import com.pavastudios.TomMaso.storagesystem.model.Commento;
import com.pavastudios.TomMaso.storagesystem.model.Messaggio;
import com.pavastudios.TomMaso.storagesystem.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * La classe CommentQueries contiene i metodi per l'interazione con il database relativi ai Commenti
 */
@SuppressWarnings("unused")
public class CommentQueries {
    public static MasterPreparedStatement FIND_COMMENT_BY_ID;
    static MasterPreparedStatement FETCH_COMMENT_FOR_PAGE;
    static MasterPreparedStatement SEND_COMMENT;
    static MasterPreparedStatement UPDATE_BLOG_COMMENTS;
    static MasterPreparedStatement MOVE_COMMENTS;
    static MasterPreparedStatement DELETE_COMMENTS_FOR_POST;
    static MasterPreparedStatement DELETE_COMMENT;

    /**
     * Inizializza le prepared statements contenenti le query relative ai commenti
     * @throws SQLException Problemi con il database
     */
    public static void initQueries() throws SQLException {
        FETCH_COMMENT_FOR_PAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `url_pagina`=? ORDER BY `data_invio`");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        SEND_COMMENT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Commento`(`mittente`,`testo`,`url_pagina`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        UPDATE_BLOG_COMMENTS = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Commento` SET `url_pagina`=CONCAT(?,RIGHT(`url_pagina`,LENGTH(`url_pagina`)-LENGTH(?)-2)) WHERE `url_pagina` LIKE ?");
        MOVE_COMMENTS = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Commento` SET `url_pagina`=? WHERE `url_pagina`=?");
        DELETE_COMMENTS_FOR_POST = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Commento` WHERE `url_pagina` LIKE ?");
        DELETE_COMMENT = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Commento` WHERE `id_commento` =?");
    }

    /**
     * Esegue la query che elimina i commenti di una pagina
     * @param postUrl Url della pagina (del tipo /NOMEBLOG/...)
     * @throws SQLException Problemi con il database
     */
    public static void deleteCommentsForBlog(String postUrl) throws SQLException {
        postUrl = Queries.escapeLike(postUrl);
        postUrl += "%";//Se si sta cancellando una cartella allora tutti quelli che iniziano con uno specifico nome devono essere cancellati
        DELETE_COMMENTS_FOR_POST.setString(1, postUrl);
        DELETE_COMMENTS_FOR_POST.executeUpdate();
    }

    /**
     * Esegue la query che recupera i commenti per una determinata pagina
     * @param page Pagina di cui recuperare i commenti
     * @return Lista di commenti recuperata
     * @throws SQLException Problemi con il database
     */
    public static List<Commento> fetchCommentsFromPage(String page) throws SQLException {
        FETCH_COMMENT_FOR_PAGE.setString(1, page);
        ResultSet rs = FETCH_COMMENT_FOR_PAGE.executeQuery();
        List<Commento> comments = Queries.resultSetToList(Entities.COMMENTO, rs);
        rs.close();
        return comments;
    }

    /**
     * Esegue la query che invia un commento
     * @param utente Utente autore del commento
     * @param messaggio Contenuto del commento
     * @param pagina Pagina da commentare
     * @return Commento appena creato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Commento sendComment(Utente utente, String messaggio, String pagina) throws SQLException {
        if (utente == null || messaggio == null || pagina == null) return null;
        SEND_COMMENT.setInt(1, utente.getIdUtente());
        SEND_COMMENT.setString(2, messaggio);
        SEND_COMMENT.setString(3, pagina);
        SEND_COMMENT.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(SEND_COMMENT);
        return CommentQueries.findCommentById(id);
    }

    /**
     * Esegue la query che trova un commento tramite id
     * @param idCommento Id da cercare
     * @return Commento trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Commento findCommentById(int idCommento) throws SQLException {
        return Queries.findById(Entities.COMMENTO, idCommento);
    }

    /**
     * Esegue la query che trova un messaggio tramite id
     * @param idMessaggio Id da cercare
     * @return Messaggio trovato
     * @throws SQLException Problemi con il database
     */
    public static @Nullable Messaggio findMessageById(int idMessaggio) throws SQLException {
        return Queries.findById(Entities.MESSAGGIO, idMessaggio);
    }

    /**
     * Esegue la query che elimina un commento
     * @param commento Commento da eliminare
     * @throws SQLException Problemi con il database
     */
    public static void deleteCommento(Commento commento) throws SQLException {
        DELETE_COMMENT.setInt(1, commento.getIdCommento());
        DELETE_COMMENT.executeUpdate();
    }
}
