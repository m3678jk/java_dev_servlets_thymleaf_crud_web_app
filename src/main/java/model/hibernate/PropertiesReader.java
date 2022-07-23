package model.hibernate;

import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Getter
public class PropertiesReader {
    private String dbUrl;
    private String dbUser;
    private String dbPass;

    public PropertiesReader() {
        List<String> list = null;
        try {
            String s = Paths.get("hibernate.properties")
                    .toAbsolutePath()
                    .toString()
                    .replace("hibernate.properties", "src/main/resources/hibernate.properties");
            list = Files.readAllLines(Path.of(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbUrl = list.stream()
                .filter(it -> it.contains("hibernate.connection.url"))
                .map(it -> it.replace("hibernate.connection.url=", ""))
                .findFirst().get();

        dbUser = list.stream()
                .filter(it -> it.contains("hibernate.connection.username"))
                .map(it -> it.replace("hibernate.connection.username=", ""))
                .findFirst().get();

        dbPass = list.stream()
                .filter(it -> it.contains("hibernate.connection.password"))
                .map(it -> it.replace("hibernate.connection.password=", ""))
                .findFirst().get();

    }

}
