package com.pavastudios.TomMaso.storage;

import com.pavastudios.TomMaso.access.UserQueries;
import com.pavastudios.TomMaso.blog.BlogQueries;
import com.pavastudios.TomMaso.blog.visualization.CommentQueries;
import com.pavastudios.TomMaso.chat.ChatQueries;
import com.pavastudios.TomMaso.report.ReportQueries;
import com.pavastudios.TomMaso.storage.model.*;


@SuppressWarnings("rawtypes")
/**
 * L'enumerazione Entities raggruppa i vari model e li collega ai metodi per trovare un'istanza tramite id
 */
public enum Entities {

    BLOG(Blog.class, BlogQueries.FIND_BLOG_BY_ID),
    CHAT(Chat.class, ChatQueries.FIND_CHAT_BY_ID),
    COMMENTO(Commento.class, CommentQueries.FIND_COMMENT_BY_ID),
    MESSAGGIO(Messaggio.class, ChatQueries.FIND_MESSAGE_BY_ID),
    UTENTE(Utente.class, UserQueries.FIND_USER_BY_ID),
    REPORT(Report.class, ReportQueries.FIND_REPORT_BY_ID);

    final Class entityClass;
    final MasterPreparedStatement findByIdStmt;

    Entities(Class entityClass, MasterPreparedStatement findByIdStmt) {
        this.entityClass = entityClass;
        this.findByIdStmt = findByIdStmt;
    }
}
