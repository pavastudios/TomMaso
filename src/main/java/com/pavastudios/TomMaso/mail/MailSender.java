package com.pavastudios.TomMaso.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSender {


    private static final String FORGOT_URL = "http://localhost:8080/TomMaso_war_exploded/forgot";
    private static final String FORGOT_SUBJECT = "TomMASO password dimenticata";

    private static final String SMTP_USERNAME = "tommasosrl@libero.it";
    private static final String SMTP_PASSWORD = System.getEnv("TOMMASO_EMAIL_PASSWORD");
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
        try {
            sendMessage(toMail, FORGOT_SUBJECT, code);
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
        message.setContent(text, "text/plain");
        Transport.send(message);
    }
}
