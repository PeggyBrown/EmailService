package com.st;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageBuilder {
    public MessageBuilder() {
    }

    MimeMessage buildMessage(MsgInfo msgInfo, Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(msgInfo.getFrom()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(msgInfo.getTo()));
        message.setSubject(msgInfo.getSubject());
        message.setText(msgInfo.getContent());
        return message;
    }
}