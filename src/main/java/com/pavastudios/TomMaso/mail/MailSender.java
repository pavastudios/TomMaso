package com.pavastudios.TomMaso.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

public class MailSender {


    private static final String FORGOT_URL = "http://localhost:8080/TomMaso_war_exploded/forgot";
    private static final String FORGOT_SUBJECT = "TomMASO password dimenticata";
    private static final String FORGOT_TEXT = String.format(Locale.US, "Url di recupero password: <a href='%s?code=%%s'>%s?code=%%s</a>", FORGOT_URL, FORGOT_URL);


    private static final String SMTP_USERNAME = "tommasosrl@libero.it";
    private static final String SMTP_PASSWORD = "m7AZpmL5RZwtJEj@";
    private static final String STMP_HOST = "smtp.libero.it";
    private static final int STMP_PORT = 465;

    private static final String MAIL_FROM = "tommasosrl@libero.it";
    private static final String MAIL_FROMNAME = "TomMASO";

    private static final Properties PROP = new Properties();
    private static final Authenticator AUTHENTICATOR = new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        }
    };

    static {
        PROP.put("mail.smtp.host", STMP_HOST);
        PROP.put("mail.smtp.socketFactory.port", STMP_PORT);
        PROP.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        PROP.put("mail.smtp.auth", "true");
        PROP.put("mail.smtp.port", STMP_PORT);
    }

    public static void sendForgotPassword(String toMail, String code) throws MessagingException {
        String text = String.format(Locale.US, FORGOT_TEXT, code, code);
        try {
            sendMessage(toMail, FORGOT_SUBJECT, text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String toMail, String subject, String text) throws MessagingException, UnsupportedEncodingException {
        Session session = Session.getDefaultInstance(PROP, AUTHENTICATOR);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MAIL_FROM, MAIL_FROMNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
        message.setSubject(subject);
        message.setContent(text, "text/html");
        Transport.send(message);
    }
}