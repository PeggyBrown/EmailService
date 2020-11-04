package com.st;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private final MessageBuilder messageBuilder;
    private final PropertiesLoader propertiesLoader;
    private final MessageValidator messageValidator;
    private final Transporter transporter;

    public EmailSender(MessageBuilder messageBuilder,
                       PropertiesLoader propertiesLoader,
                       MessageValidator messageValidator,
                       Transporter transporter) {

        this.messageBuilder = messageBuilder;
        this.propertiesLoader = propertiesLoader;
        this.messageValidator = messageValidator;
        this.transporter = transporter;
    }

    void sendEmail(String to, String subject, String content) {
        Properties prop = propertiesLoader.loadProperties();
        MsgInfo msgInfo = new MsgInfo(propertiesLoader.getFrom(), to, subject, content);
        messageValidator.validateMessage(msgInfo);
        Session session = Session.getDefaultInstance(prop);
        session.setDebug(true);

        try {
            MimeMessage message = messageBuilder.buildMessage(msgInfo, session);
            transporter.sendMessage(message, propertiesLoader.getUsername(), propertiesLoader.getPassword());

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}