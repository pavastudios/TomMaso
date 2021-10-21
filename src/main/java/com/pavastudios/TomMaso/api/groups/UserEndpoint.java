package com.pavastudios.TomMaso.api.groups;

import com.pavastudios.TomMaso.api.components.*;
import com.pavastudios.TomMaso.db.queries.Queries;
import com.pavastudios.TomMaso.mail.MailSender;
import com.pavastudios.TomMaso.model.Utente;
import com.pavastudios.TomMaso.utility.Utility;

import javax.mail.MessagingException;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class UserEndpoint {
    @Endpoint(value = "/user/send-forgot-code", requireLogin = false, params = {
            @ApiParameter(value = "email", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage FORGOT_CODE_ACTION = (parser, writer, user) -> {
        String email = parser.getValueString("email");
        String code = Queries.forgetPassword(email);
        if (code == null) {

            throw new ApiException(400, "Email non valida");
        }
        try {
            MailSender.sendForgotPassword(email, code);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new ApiException(400, "Impossibile inviare email");
        }
        writer.name(ApiManager.OK_PROP).value("OK");
    };
    @Endpoint(value = "/user/change-password", requireLogin = false, params = {
            @ApiParameter(value = "password1", type = ApiParam.Type.STRING),
            @ApiParameter(value = "password2", type = ApiParam.Type.STRING),
            @ApiParameter(value = "code", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage CHANGE_PASSWORD_ACTION = (parser, writer, user) -> {
        String password1 = parser.getValueString("password1");
        String password2 = parser.getValueString("password2");
        String code = parser.getValueString("code");
        try {
            Utility.fromHexString(code);
        } catch (NumberFormatException ignore) {
            throw new ApiException(400, "Invalid 'code'");
        }
        if (password1 == null || !password1.equals(password2)) {
            throw new ApiException(400, "Password diverse");
        }
        user = Queries.findUserFromForgot(code);
        if (user == null) {
            throw new ApiException(400, "Codice errato");
        }
        try {
            Queries.changePassword(user, password1);
            Queries.deleteForget(code);
        } catch (SQLException e) {
            throw new ApiException(400, "Impossibile cambiare password");
        }
        writer.name(ApiManager.OK_PROP).value("changed");
    };
    @Endpoint(value = "/user/find-user", requireLogin = false, params = {
            @ApiParameter(value = "username", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage FIND_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(400, "username non trovato");
        }
        writer.name(ApiManager.OK_PROP);
        u.writeJson(writer);
    };

    @Endpoint(value = "/user/add-admin", requireLogin = true, params = {
            @ApiParameter(value = "username", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage ADMIN_ADD_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(400, "Utente non trovato");
        }
        Queries.changeRole2(u, true);
        writer.name(ApiManager.OK_PROP).value("ok");
    };
    @Endpoint(value = "/user/remove-admin", requireLogin = true, params = {
            @ApiParameter(value = "username", type = ApiParam.Type.STRING)
    })
    public static final ApiEndpoint.Manage ADMIN_DEL_ACTION = (parser, writer, user) -> {
        String username = parser.getValueString("username");
        Utente u = Queries.findUserByUsername(username);
        if (u == null) {
            throw new ApiException(400, "Utente non trovato");
        }
        Queries.changeRole2(u, false);
        writer.name(ApiManager.OK_PROP).value("ok");
    };
}
