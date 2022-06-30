package model;

import model.commandsDB.prefs.Prefs;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public void initDB(DatabaseConnector databaseConnector){
        String dbUrl = new Prefs().getString(Prefs.DB_JDBC_CONNECTION_URL);
        String dbUser = new Prefs().getString(Prefs.DB_JDBC_USER);
        String dbPass = new Prefs().getString(Prefs.DB_JDBC_PASSWORD);

        Flyway flyway = Flyway
                .configure()
                .dataSource(dbUrl, dbUser, dbPass).load();

        // Start the migration
        flyway.migrate();
    }
    }

