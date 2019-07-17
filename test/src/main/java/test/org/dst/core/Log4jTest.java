package test.org.dst.core;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class Log4jTest {

    public String readLog(String path) {
        File file = new File(path);
        StringBuffer s = new StringBuffer();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                s.append(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return s.toString();
    }

    public void initLog(String str) {
        PropertyConfigurator.configure( "./src/log4j.properties" );
        Logger logger = Logger.getLogger(Log4jTest. class );
        logger.debug(str + ": debug");
        logger.info(str + ": info");
        logger.warn(str + ": warn");
        logger.error(str + ": error");
    }

    @Test
    public void testDebug() {
        initLog("testDebug");
        String line = readLog("./src/logs/debug.log");
        Assertions.assertTrue(line.contains("[DEBUG] testDebug: debug"));
        Assertions.assertTrue(line.contains("[INFO] testDebug: info"));
        Assertions.assertTrue(line.contains("[WARN] testDebug: warn"));
        Assertions.assertTrue(line.contains("[ERROR] testDebug: error"));
    }

    @Test
    public void testInfo() {
        initLog("testInfo");
        String line = readLog("./src/logs/info.log");
        Assertions.assertFalse(line.contains("[DEBUG] testInfo: debug"));
        Assertions.assertTrue(line.contains("[INFO] testInfo: info"));
        Assertions.assertTrue(line.contains("[WARN] testInfo: warn"));
        Assertions.assertTrue(line.contains("[ERROR] testInfo: error"));
    }

    @Test
    public void testWarn() {
        initLog("testWarn");
        String line = readLog("./src/logs/warn.log");
        Assertions.assertFalse(line.contains("[DEBUG] testWarn: debug"));
        Assertions.assertFalse(line.contains("[INFO] testWarn: info"));
        Assertions.assertTrue(line.contains("[WARN] testWarn: warn"));
        Assertions.assertTrue(line.contains("[ERROR] testWarn: error"));
    }

    @Test
    public void testError() {
        initLog("testError");
        String line = readLog("./src/logs/error.log");
        Assertions.assertFalse(line.contains("[DEBUG] testError: debug"));
        Assertions.assertFalse(line.contains("[INFO] testError: info"));
        Assertions.assertFalse(line.contains("[WARN] testError: warn"));
        Assertions.assertTrue(line.contains("[ERROR] testError: error"));
    }

}
