package model.hibernate;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Getter
public class PropertiesReader {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public PropertiesReader() {
        FileReader reader= null;
        try {
            reader = new FileReader(Paths.get("hibernate.properties")
                    .toAbsolutePath()
                    .toString()
                    .replace("hibernate.properties", "src/main/resources/hibernate.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties p=new Properties();
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
