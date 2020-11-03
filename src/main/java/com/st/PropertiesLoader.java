package com.st;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private Properties prop;

    public PropertiesLoader() {
        prop = new Properties();
    }

    public Properties loadProperties() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {

            prop.load(input);

            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }

    String getPassword() {
        return prop.getProperty("mail.smtp.password");
    }

    String getUsername() {
        return prop.getProperty("mail.smtp.username");
    }

    String getFrom() {
        return prop.getProperty("from");
    }
}