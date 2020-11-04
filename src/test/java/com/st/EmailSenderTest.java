package com.st;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EmailSenderTest {

    String fromEmail = "from@email.com";
    String toEmail = "to@email.com";
    String subject = "Subject";
    String text = "Text";

    EmailSender sender;

    @Mock
    MessageBuilder messageBuilder;

    @Mock
    PropertiesLoader propertiesLoader;

    @Mock
    MessageValidator messageValidator;

    @Mock
    Transporter transporter;

    Session session;

    Properties properties;

    @BeforeEach
    void setUp() {
        initMocks(this);
        sender = new EmailSender(
                messageBuilder,
                propertiesLoader,
                messageValidator,
                transporter);
        when(propertiesLoader.loadProperties()).thenReturn(properties);
        properties = new Properties();
        session = Session.getDefaultInstance(properties);
    }

    @Test
    void shouldSendEmail() throws MessagingException {
        //given
        when(propertiesLoader.loadProperties()).thenReturn(properties);
        MimeMessage mimeMessage = prepareMessage();
        when(messageBuilder.buildMessage(any(MsgInfo.class), any(Session.class))).thenReturn(mimeMessage);

        //when
        sender.sendEmail(toEmail, subject, text);

        //then
        ArgumentCaptor<MsgInfo> argument = ArgumentCaptor.forClass(MsgInfo.class);
        Mockito.verify(messageValidator).validateMessage(argument.capture());
        Assertions.assertThat(subject).isEqualTo(argument.getValue().getSubject());
    }

    @Test
    void shouldNotSendEmailWhenAddressIncorrect() {
        //given
        doThrow(InvalidEmailException.class).when(messageValidator).validateMessage(any(MsgInfo.class));
        when(propertiesLoader.loadProperties()).thenReturn(properties);

        //then
        assertThatExceptionOfType(InvalidEmailException.class).isThrownBy(() -> {
            sender.sendEmail(toEmail, subject, text);
        });
    }

    private MimeMessage prepareMessage() throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(fromEmail);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(text);
        return mimeMessage;
    }
}