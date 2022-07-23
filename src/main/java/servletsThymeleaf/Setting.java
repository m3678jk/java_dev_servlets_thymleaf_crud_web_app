package servletsThymeleaf;


import java.nio.file.Path;
import java.nio.file.Paths;

public class Setting {

    public String init(){
        Path path = Paths.get("templates");
        String result = path.toAbsolutePath().toString().replace("templates", "") + "src/main/resources/templates/";
        return result;
    }
}
