package com.st;

public class Application {

    public static void main(String[] args) {

        MessageBuilder messageBuilder = new MessageBuilder();
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        MessageValidator messageValidator = new MessageValidator();
        Transporter transporter = new Transporter();
        EmailSender sender = new EmailSender(messageBuilder, propertiesLoader, messageValidator, transporter);
        sender.sendEmail("your@mail.com", "System test", "Hello world!");

    }
}