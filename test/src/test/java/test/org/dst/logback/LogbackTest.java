package test.org.dst.logback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackTest.class.getName());

    @Test
    public void testLogback() {
        LOGGER.info("This is info message");
        LOGGER.debug("This is debug message");
        LOGGER.error("This is error message");
        LOGGER.trace("This is trace message");
        LOGGER.warn("This is warn message");
    }

}
