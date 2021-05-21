package com.pavastudios.TomMaso.db.queries;

import com.pavastudios.TomMaso.db.connection.GlobalConnection;
import com.pavastudios.TomMaso.db.connection.MasterPreparedStatement;
import com.pavastudios.TomMaso.model.*;
import com.pavastudios.TomMaso.utility.Security;
import com.pavastudios.TomMaso.utility.Utility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Queries {

    static MasterPreparedStatement FIND_USER_BY_USERNAME;
    static MasterPreparedStatement FIND_USER_BY_ID;
    static MasterPreparedStatement FIND_PAGE_BY_ID;
    static MasterPreparedStatement FIND_BLOG_BY_ID;
    static MasterPreparedStatement FIND_COMMENT_BY_ID;
    static MasterPreparedStatement FIND_MESSAGE_BY_ID;
    static MasterPreparedStatement FIND_CHAT_BY_ID;
    static MasterPreparedStatement FIND_USER_BY_COOKIE;
    static MasterPreparedStatement REGISTER_USER;
    static MasterPreparedStatement REGISTER_REMEMBER_ME;
    static MasterPreparedStatement DELETE_REMEMBER_ME;
    static MasterPreparedStatement CREATE_CHAT;
    static MasterPreparedStatement CREATE_BLOG;
    static MasterPreparedStatement SEND_MESSAGE;
    static MasterPreparedStatement FETCH_CHAT_MESSAGE;
    static MasterPreparedStatement FIND_USER_CHAT;
    static MasterPreparedStatement FIND_CHAT_BY_USERS;
    static MasterPreparedStatement FIND_BLOGS_OWNED_BY;
    static MasterPreparedStatement CREATE_FORGET_COOKIE;
    static MasterPreparedStatement FIND_USER_BY_EMAIL;
    static MasterPreparedStatement FIND_USER_FROM_FORGOT;
    static MasterPreparedStatement CHANGE_PASSWORD;
    static MasterPreparedStatement DELETE_FORGET;
    static MasterPreparedStatement FIND_BLOG_BY_NAME;
    static MasterPreparedStatement DELETE_BLOG;
    static MasterPreparedStatement UPDATE_BLOG_NAME;
    static MasterPreparedStatement UPDATE_USER_DATA;
    static MasterPreparedStatement FETCH_MESSAGE_FROM_ID;
    static MasterPreparedStatement FETCH_COMMENT_FOR_PAGE;
    static MasterPreparedStatement SEND_COMMENT;
    static MasterPreparedStatement BLOG_INCREMENT;
    static MasterPreparedStatement TOP_BLOG;

    public static void initQueries() throws SQLException {
        //FETCH_CHAT_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente` IN ((SELECT `utente2` FROM 'Chat' WHERE `utente1`=?) UNION (SELECT `utente1` FROM 'Chat' WHERE `utente2`=?))");
        TOP_BLOG = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` ORDER BY `visite` DESC LIMIT ?");
        FETCH_COMMENT_FOR_PAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `url_pagina`=? ORDER BY `data_invio`");
        FETCH_CHAT_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_chat`=? ORDER BY `data_invio` DESC LIMIT ? OFFSET ?");
        FETCH_MESSAGE_FROM_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_chat`=? AND `id_messaggio`>? ORDER BY `data_invio`");
        FIND_USER_BY_USERNAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `username`=?");
        FIND_USER_BY_EMAIL = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `email`=?");
        FIND_USER_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=?");
        FIND_PAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Pagina` WHERE `id_pagina`=?");
        FIND_BLOG_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `id_blog`=?");
        FIND_BLOGS_OWNED_BY = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `proprietario`=? ORDER BY `nome`");
        FIND_COMMENT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Commento` WHERE `id_commento`=?");
        FIND_USER_CHAT = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? OR `utente2`=?");
        FIND_MESSAGE_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Messaggio` WHERE `id_messaggio`=?");
        FIND_CHAT_BY_ID = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `id_chat`=?");
        FIND_CHAT_BY_USERS = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Chat` WHERE `utente1`=? AND `utente2`=? ");
        FIND_USER_BY_COOKIE = GlobalConnection.CONNECTION.prepareStatement("SELECT `id_utente` FROM `RememberMe` WHERE `cookie`=?");
        REGISTER_USER = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Utente`(`email`,`password`,`username`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        REGISTER_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `RememberMe`(`id_utente`,`cookie`) VALUES (?,?)");
        CREATE_CHAT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Chat`(`utente1`,`utente2`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        SEND_MESSAGE = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Messaggio`(`id_chat`,`mittente`,`testo`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        SEND_COMMENT = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Commento`(`mittente`,`testo`,`url_pagina`) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        DELETE_REMEMBER_ME = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `RememberMe` WHERE `cookie`=?");
        CREATE_BLOG = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `Blog`(`proprietario`,`nome`) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        CHANGE_PASSWORD = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `password`=? WHERE `id_utente`=?");
        BLOG_INCREMENT = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `visite`=`visite`+1 WHERE `id_blog`=?");
        CREATE_FORGET_COOKIE = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `PasswordReset`(`codice`,`id_utente`) VALUES (?,?)");
        FIND_USER_FROM_FORGOT = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=(SELECT `id_utente` FROM `PasswordReset` WHERE `codice`=?)");
        DELETE_FORGET = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `PasswordReset` WHERE `codice`=?");
        FIND_BLOG_BY_NAME = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Blog` WHERE `nome`=?");
        CREATE_FORGET_COOKIE = GlobalConnection.CONNECTION.prepareStatement("INSERT INTO `PasswordReset`(`codice`,`id_utente`) VALUES (?,?)");
        FIND_USER_FROM_FORGOT = GlobalConnection.CONNECTION.prepareStatement("SELECT * FROM `Utente` WHERE `id_utente`=(SELECT `id_utente` FROM `PasswordReset` WHERE `codice`=?)");
        DELETE_FORGET = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `PasswordReset` WHERE `codice`=?");
        DELETE_BLOG = GlobalConnection.CONNECTION.prepareStatement("DELETE FROM `Blog` WHERE `id_blog`=?");
        UPDATE_BLOG_NAME = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Blog` SET `nome`=? WHERE `id_blog`=?");
        UPDATE_USER_DATA = GlobalConnection.CONNECTION.prepareStatement("UPDATE `Utente` SET `username`=?,`bio`=? WHERE `id_utente`=?");
    }

    public static List<Commento>fetchCommentsFromPage(String page) throws SQLException {
        FETCH_COMMENT_FOR_PAGE.setString(1,page);
        ResultSet rs=FETCH_COMMENT_FOR_PAGE.executeQuery();
        List<Commento>comments=resultSetToList(Entities.COMMENTO,rs);
        rs.close();
        return comments;
    }

    public static List<Blog>topBlogs(int count) throws SQLException {
        TOP_BLOG.setInt(1,count);
        ResultSet rs=TOP_BLOG.executeQuery();
        List<Blog>blogs=resultSetToList(Entities.BLOG,rs);
        rs.close();
        return blogs;
    }

    public static Utente findUserFromForgot(String code) throws SQLException {
        byte[] bytes = Utility.fromHexString(code);
        FIND_USER_FROM_FORGOT.setBytes(1, bytes);
        ResultSet rs = FIND_USER_FROM_FORGOT.executeQuery();
        Utente u = null;
        if (rs.first()) {
            u = resultSetToModel(Entities.UTENTE, rs);
        }
        rs.close();
        return u;

    }

    public static String forgetPassword(String email) throws SQLException {
        Utente user = findUserByEmail(email);
        if (user == null) return null;
        byte[] code = Security.generateRandomBytes(32);
        String codice = Utility.toHexString(code);
        CREATE_FORGET_COOKIE.setBytes(1, code);
        CREATE_FORGET_COOKIE.setInt(2, user.getIdUtente());
        CREATE_FORGET_COOKIE.executeUpdate();
        return codice;
    }

    public static List<Blog> getBlogsUser(Utente u) throws SQLException {
        FIND_BLOGS_OWNED_BY.setInt(1, u.getIdUtente());
        ResultSet rs = FIND_BLOGS_OWNED_BY.executeQuery();
        List<Blog> blogList = resultSetToList(Entities.BLOG, rs);
        rs.close();
        return blogList;
    }

    public static Blog createBlog(Utente utente, String nome) throws SQLException {
        CREATE_BLOG.setInt(1, utente.getIdUtente());
        CREATE_BLOG.setString(2, nome);
        CREATE_BLOG.executeUpdate();
        int idBlog = Utility.getIdFromGeneratedKeys(CREATE_BLOG);
        return findBlogById(idBlog);
    }

    public static List<Messaggio> fetchMessages(Chat chat) throws SQLException {
        return fetchMessages(chat, Integer.MAX_VALUE, 0);
    }

    public static List<Messaggio> fetchMessages(Chat chat, int amount, int offset) throws SQLException {
        FETCH_CHAT_MESSAGE.setInt(1, chat.getIdChat());
        FETCH_CHAT_MESSAGE.setInt(2, amount);
        FETCH_CHAT_MESSAGE.setInt(3, offset);
        ResultSet rs = FETCH_CHAT_MESSAGE.executeQuery();
        List<Messaggio> messages = resultSetToList(Entities.MESSAGGIO, rs);
        Collections.reverse(messages);
        rs.close();
        return messages;
    }

    public static @Nullable Commento sendComment(Utente utente, String messaggio, String pagina) throws SQLException {
        if (utente == null || messaggio == null || pagina == null) return null;
        SEND_COMMENT.setInt(1, utente.getIdUtente());
        SEND_COMMENT.setString(2, messaggio);
        SEND_COMMENT.setString(3, pagina);
        SEND_COMMENT.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(SEND_COMMENT);
        return findCommentById(id);
    }

    public static @Nullable Messaggio sendTextToChat(Chat chat, Utente mittente, String testo) throws SQLException {
        if (chat == null || mittente == null || testo == null) return null;
        SEND_MESSAGE.setInt(1, chat.getIdChat());
        SEND_MESSAGE.setInt(2, mittente.getIdUtente());
        SEND_MESSAGE.setString(3, testo);
        SEND_MESSAGE.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(SEND_MESSAGE);
        return findMessageById(id);
    }

    public static Chat createChat(Utente u1, Utente u2) throws SQLException {
        Utente tmp;
        if (u1 == null || u2 == null) return null;

        if (u1.getIdUtente() > u2.getIdUtente()) {//in ordine di id
            tmp = u1;
            u1 = u2;
            u2 = tmp;
        }

        CREATE_CHAT.setInt(1, u1.getIdUtente());
        CREATE_CHAT.setInt(2, u2.getIdUtente());
        CREATE_CHAT.executeUpdate();
        int id = Utility.getIdFromGeneratedKeys(CREATE_CHAT);
        return findChatById(id);
    }

    public static void removeCookie(String cookie) throws SQLException {
        byte[] bytes = Utility.fromHexString(cookie);
        DELETE_REMEMBER_ME.setBytes(1, bytes);
        DELETE_REMEMBER_ME.executeUpdate();
    }

    @SuppressWarnings("all")
    private static <T> @Nullable T resultSetToModel(Entities entity, ResultSet rs) {
        T result = null;
        try {
            result = (T) entity.entityClass.getMethod("fromResultSet", ResultSet.class).invoke(null, rs);
        } catch (Exception ignore) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T findById(Entities entity, int id) throws SQLException {
        entity.findByIdStmt.setInt(1, id);
        ResultSet rs = entity.findByIdStmt.executeQuery();
        Object result = null;
        if (rs.first())
            result = resultSetToModel(entity, rs);
        rs.close();
        return (T) result;
    }

    @SuppressWarnings("all")
    public static <T> @NotNull List<T> resultSetToList(Entities entity, ResultSet rs) throws SQLException {
        ArrayList<T> list = new ArrayList<>();
        if (rs.first()) {
            do {
                T result = resultSetToModel(entity, rs);
                list.add(result);
            } while (rs.next());
        }
        list.trimToSize();
        return list;
    }

    //Query Utente
    public static @Nullable Utente findUserByUsername(@NotNull String username) throws SQLException {
        FIND_USER_BY_USERNAME.setString(1, username);
        ResultSet rs = FIND_USER_BY_USERNAME.executeQuery();
        Utente user = null;
        if (rs.first())
            user = Utente.fromResultSet(rs);
        rs.close();
        return user;
    }

    //Query Utente
    public static @Nullable Utente findUserByEmail(@NotNull String email) throws SQLException {
        FIND_USER_BY_EMAIL.setString(1, email);
        ResultSet rs = FIND_USER_BY_EMAIL.executeQuery();
        Utente user = null;
        if (rs.first())
            user = Utente.fromResultSet(rs);
        rs.close();
        return user;
    }

    public static @Nullable Utente findUserById(int idUtente) throws SQLException {
        return findById(Entities.UTENTE, idUtente);
    }

    public static @Nullable Utente registerUser(String email, String password, String username) throws SQLException {
        REGISTER_USER.setString(1, email);
        REGISTER_USER.setString(2, Security.crypt(password));
        REGISTER_USER.setString(3, username);
        REGISTER_USER.executeUpdate();
        int newId = Utility.getIdFromGeneratedKeys(REGISTER_USER);
        return findUserById(newId);
    }

    public static @Nullable Utente login(String username, String password) throws SQLException {
        Utente u = findUserByUsername(username);
        if (u == null) return null;
        return u.userVerifyLogin(password)?u:null;
    }

    //Query Pagina
    public static @Nullable Pagina findPageById(int idPagina) throws SQLException {
        return findById(Entities.PAGINA, idPagina);
    }

    //Query Blog
    public static @Nullable Blog findBlogById(int idBlog) throws SQLException {
        return findById(Entities.BLOG, idBlog);
    }

    public static void incrementVisit(Blog blog) throws SQLException{
        if(blog==null)return;
        BLOG_INCREMENT.setInt(1,blog.getIdBlog());
        BLOG_INCREMENT.executeUpdate();
    }

    public static @Nullable Blog findBlogByName(@NotNull String name) throws SQLException {
        FIND_BLOG_BY_NAME.setString(1, name);
        ResultSet rs = FIND_BLOG_BY_NAME.executeQuery();
        Blog blog = null;
        if (rs.first())
            blog = Blog.fromResultSet(rs);
        rs.close();
        return blog;
    }

    //Query Commento
    public static @Nullable Commento findCommentById(int idCommento) throws SQLException {
        return findById(Entities.COMMENTO, idCommento);
    }

    //Query Messaggio
    public static @Nullable Messaggio findMessageById(int idMessaggio) throws SQLException {
        return findById(Entities.MESSAGGIO, idMessaggio);
    }

    //Query Chat
    public static @Nullable Chat findChatById(int idChat) throws SQLException {
        return findById(Entities.CHAT, idChat);
    }

    //Query RememberMe
    public static @Nullable Utente findUserByCookie(String cookie) throws SQLException {
        if (cookie == null) return null;
        byte[] bytes = Utility.fromHexString(cookie);
        FIND_USER_BY_COOKIE.setBytes(1, bytes);
        ResultSet rs = FIND_USER_BY_COOKIE.executeQuery();
        int id = -1;
        if (rs.first())
            id = rs.getInt("id_utente");
        rs.close();
        return id == -1 ? null : findUserById(id);
    }

    public static @Nullable byte[] registerRememberMe(Utente u) throws SQLException {
        byte[] cookie = Utility.generateRememberMeCookie();
        REGISTER_REMEMBER_ME.setInt(1, u.getIdUtente());
        REGISTER_REMEMBER_ME.setBytes(2, cookie);
        REGISTER_REMEMBER_ME.executeUpdate();
        return cookie;
    }

    public static @NotNull List<Chat> findUserChat(Utente u1) throws SQLException {
        FIND_USER_CHAT.setInt(1, u1.getIdUtente());
        FIND_USER_CHAT.setInt(2, u1.getIdUtente());
        ResultSet rs = FIND_USER_CHAT.executeQuery();
        List<Chat> chats = Queries.resultSetToList(Entities.CHAT, rs);
        rs.close();
        return chats;
    }

    public static @Nullable Chat findChatByUsers(Utente u1, Utente u2) throws SQLException {
        if (u1.getIdUtente() > u2.getIdUtente()) {
            Utente temp = u1;
            u1 = u2;
            u2 = temp;
        }
        FIND_CHAT_BY_USERS.setInt(1, u1.getIdUtente());
        FIND_CHAT_BY_USERS.setInt(2, u2.getIdUtente());
        ResultSet rs = FIND_CHAT_BY_USERS.executeQuery();
        rs.first();
        Chat chat=resultSetToModel(Entities.CHAT,rs);
        rs.close();
        return chat;
    }

    public static void changePassword(Utente user, String password) throws SQLException {
        String hashed=Security.crypt(password);
        CHANGE_PASSWORD.setString(1, hashed);
        CHANGE_PASSWORD.setInt(2, user.getIdUtente());
        CHANGE_PASSWORD.executeUpdate();
    }

    public static void deleteForget(String code) throws SQLException {
        byte[] bytes = Utility.fromHexString(code);
        DELETE_FORGET.setBytes(1, bytes);
        DELETE_FORGET.executeUpdate();
    }

    public static void deleteBlog(Blog blog) throws SQLException {
        DELETE_BLOG.setInt(1,blog.getIdBlog());
        DELETE_BLOG.executeUpdate();
    }

    public static boolean inviaMessaggio(Utente mittente, Utente destinatario, String messaggio) throws SQLException {
        if (Queries.findChatByUsers(mittente, destinatario) == null)
            Queries.createChat(mittente, destinatario);

        Chat chat = Queries.findChatByUsers(mittente, destinatario);
        SEND_MESSAGE.setInt(1, chat.getIdChat());
        SEND_MESSAGE.setInt(2, mittente.getIdUtente());
        SEND_MESSAGE.setString(3, messaggio);
        SEND_MESSAGE.executeUpdate();
        return true;
    }

    public static void renameBlog(Blog fromBlog, String toName) throws SQLException{
        UPDATE_BLOG_NAME.setString(1,toName);
        UPDATE_BLOG_NAME.setInt(2,fromBlog.getIdBlog());
        UPDATE_BLOG_NAME.executeUpdate();
    }

    public static void updateUser(Utente user, String newUsername, String bio) throws SQLException {
        UPDATE_USER_DATA.setString(1,newUsername.isEmpty()?user.getUsername():newUsername);
        UPDATE_USER_DATA.setString(2,bio.isEmpty()?user.getBio():bio);
        UPDATE_USER_DATA.setInt(3,user.getIdUtente());
        UPDATE_USER_DATA.executeUpdate();
    }

    public static List<Messaggio> fetchMessageFromId(Chat chat, int fromId) throws SQLException {
        FETCH_MESSAGE_FROM_ID.setInt(1,chat.getIdChat());
        FETCH_MESSAGE_FROM_ID.setInt(2,fromId);
        ResultSet set= FETCH_MESSAGE_FROM_ID.executeQuery();
        List<Messaggio>messages=resultSetToList(Entities.MESSAGGIO,set);
        set.close();
        return messages;
    }
}
