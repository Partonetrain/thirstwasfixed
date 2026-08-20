package info.partonetrain.thirstwasfixed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EarlyConfigHelper {

    public static boolean configRead = false;
    public static int fireResDuration = -1, longFireResDuration = -1;

    static public void readConfigsEarly(){
        if(configRead){
            return;
        }

        final String configFileLoc = System.getProperty("user.dir") + "\\config\\thirstwasfixed-startup.toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);

            Pattern regDur = Pattern.compile("\\s+\"Fire Resistance Duration\" *= *([-+]?[0-9]*.?[0-9]+)");
            Pattern longDur = Pattern.compile("\\s+\"Long Fire Resistance Duration\" *= *([-+]?[0-9]*.?[0-9]+)");

            for (String line : allLines) {
                if(fireResDuration != -1 && longFireResDuration != -1){
                    break;
                }
                if (fireResDuration == -1) {
                    Matcher matcher = regDur.matcher(line);
                    if(matcher.matches()){
                        int value = Integer.parseInt(matcher.group(1));
                        if(value != 3600){
                            fireResDuration = value;
                        }
                    }
                }
                if (longFireResDuration == -1) {
                    Matcher matcher = longDur.matcher(line);
                    if(matcher.matches()){
                        int value = Integer.parseInt(matcher.group(1));
                        if(value != 3600){
                            longFireResDuration = value;
                        }
                    }
                }
            }
            configRead = true;


        } catch (IOException e) {
            ThirstWasFixedMod.LOGGER.error("Could not read config early: " + e);
            configRead = true;
        }
    }
}
