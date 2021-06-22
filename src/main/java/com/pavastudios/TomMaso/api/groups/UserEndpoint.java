package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.ApiEndpoint;
import com.pavastudios.TomMaso.api.components.ApiGroup;
import com.pavastudios.TomMaso.api.components.ApiManager;
import com.pavastudios.TomMaso.api.components.ApiParam;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.mail.MailSender;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class UserEndpoint {
    public static final String GROUP_NAME = "user";
    private static final String FETCH_ENDPOINT_NAME = "find-user";
    private static final String ADMIN_ADD_ENDPOINT_NAME = "add-admin";
    private static final String ADMIN_DEL_ENDPOINT_NAME = "remove-admin";
    private static final String FORGOT_CODE_ENDPOINT_NAME = "send-forgot-code";
    private static final String CHANGE_PASSWORD_ENDPOINT_NAME = "change-password";

    private static final ApiEndpoint.Manage FORGOT_CODE_ACTION = (parser, writer, user) -> {
        String email = parser.getValueString("email");
        String code = Queries.forgetPassword(email);
        if (code == null) {
            writer.name(ApiManager.ERROR_PROP).value("Email non valida");
            return;
        }
        try {
            MailSender.sendForgotPassword(email, code);
        } catch (MessagingException e) {
            e.printStackTrace();
            writer.name(ApiManager.ERROR_PROP).value("Impossibile inviare email");
            return;
        }
        writer.name(ApiManager.OK_PROP).value("OK");
    };
    private static final ApiEndpoint.Manage CHANGE_PASSWORD_ACTION = (parser, writer, user) -> {
        String password1 = parser.getValueString("password1");
        String password2 = parser.getValueString("password2");
        String code = parser.getValueString("code");
        try {
            Utility.fromHexString(code);
        } catch (NumberFormatException ignore) {
            writer.name(ApiManager.ERROR_PROP).value("Invalid 'code'");
            return;
        }
        if (password1 == null || !password1.equals(password2)) {
            writer.name(ApiManager.ERROR_PROP).value("Password diverse");
            return;
        }
        user = Queries.findUserFromForgot(code);
        if (user == null) {
            writer.name(ApiManager.ERROR_PROP).value("Codice errato");
            return;
        }
        try {
            Queries.changePassword(user, password1);
            Queries.deleteForget(code);
        } catch (SQLException e) {
            writer.name(ApiManager.ERROR_PROP).value("Impossibile cambiare password");
            return;
        }
        writer.name(ApiManager.OK_PROP).value("changed");
    };
    private static final ApiEndpoint.Manage FIND_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            writer.name(ApiManager.ERROR_PROP).value("username non trovato");
            return;
        }
        writer.name(ApiManager.OK_PROP);
        u.writeJson(writer);
    };


    private static final ApiEndpoint.Manage ADMIN_ADD_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            writer.name(ApiManager.ERROR_PROP).value("Utente non trovato");
            return;
        }
        Queries.changeRole2(u, true);
        writer.name(ApiManager.OK_PROP).value("ok");
    };
    private static final ApiEndpoint.Manage ADMIN_DEL_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            writer.name(ApiManager.ERROR_PROP).value("Utente non trovato");
            return;
        }
        Queries.changeRole2(u, false);
        writer.name(ApiManager.OK_PROP).value("ok");
    };


    public static final ApiGroup ENDPOINTS = new ApiGroup(GROUP_NAME,
            new ApiEndpoint(FETCH_ENDPOINT_NAME, true, FIND_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(ADMIN_ADD_ENDPOINT_NAME, true, ADMIN_ADD_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(ADMIN_DEL_ENDPOINT_NAME, true, ADMIN_DEL_ACTION,
                    new ApiParam("username", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(FORGOT_CODE_ENDPOINT_NAME, false, FORGOT_CODE_ACTION,
                    new ApiParam("email", ApiParam.Type.STRING)
            ),
            new ApiEndpoint(CHANGE_PASSWORD_ENDPOINT_NAME, false, CHANGE_PASSWORD_ACTION,
                    new ApiParam("code", ApiParam.Type.STRING),
                    new ApiParam("password1", ApiParam.Type.STRING),
                    new ApiParam("password2", ApiParam.Type.STRING)
            )
    );
}
