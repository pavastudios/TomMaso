package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.*;


@SuppressWarnings("rawtypes")
public enum Entities {
    BLOG(Blog.class, Queries.FIND_BLOG_BY_ID),
    CHAT(Chat.class, Queries.FIND_CHAT_BY_ID),
    COMMENTO(Commento.class, Queries.FIND_COMMENT_BY_ID),
    MESSAGGIO(Messaggio.class, Queries.FIND_MESSAGE_BY_ID),
    PAGINA(Pagina.class, Queries.FIND_PAGE_BY_ID),
    UTENTE(Utente.class, Queries.FIND_USER_BY_ID);

    Class entityClass;
    MasterPreparedStatement findByIdStmt;

    Entities(Class entityClass, MasterPreparedStatement findByIdStmt) {
        this.entityClass = entityClass;
        this.findByIdStmt = findByIdStmt;
    }
}
