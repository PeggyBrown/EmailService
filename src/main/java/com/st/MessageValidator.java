package com.st;

public class MessageValidator {
    public MessageValidator() {
    }

    void validateMessage(MsgInfo msgInfo) {
        if (msgInfo.getSubject().isEmpty()) {
            throw new InvalidEmailException("Empty subject is not allowed");
        }
        if (msgInfo.getTo().matches("@")) {
            throw new InvalidEmailException("Email address of recipient invalid");
        }
    }
}