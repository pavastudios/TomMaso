package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.db.queries.entities.*;
import com.pavastudios.TomMaso.model.*;


@SuppressWarnings("rawtypes")
public enum Entities {

    BLOG(Blog.class, BlogQueries.FIND_BLOG_BY_ID),
    CHAT(Chat.class, ChatQueries.FIND_CHAT_BY_ID),
    COMMENTO(Commento.class, CommentQueries.FIND_COMMENT_BY_ID),
    MESSAGGIO(Messaggio.class, ChatQueries.FIND_MESSAGE_BY_ID),
    UTENTE(Utente.class, UserQueries.FIND_USER_BY_ID),
    REPORT(Report.class, ReportQueries.FIND_REPORT_BY_ID);

    Class entityClass;
    MasterPreparedStatement findByIdStmt;

    Entities(Class entityClass, MasterPreparedStatement findByIdStmt) {
        this.entityClass = entityClass;
        this.findByIdStmt = findByIdStmt;
    }
}
