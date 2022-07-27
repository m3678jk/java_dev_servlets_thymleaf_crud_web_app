package model.hibernate;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

@Getter
public class PropertiesReader {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public PropertiesReader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reader = loader.getResourceAsStream("hibernate.properties");

        Properties p = new Properties();
        InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream("hibernate.properties");
        try {
            p.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dbUrl = p.getProperty("hibernate.connection.url");

        dbUser = p.getProperty("hibernate.connection.username");

        dbPass = p.getProperty("hibernate.connection.password");
    }
}
