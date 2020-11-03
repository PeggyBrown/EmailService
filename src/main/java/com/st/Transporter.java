package com.st;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class Transporter {
    public Transporter() {
    }

    void sendMessage(MimeMessage message, String username, String password) throws MessagingException {
        Transport.send(message, username, password);
    }
}