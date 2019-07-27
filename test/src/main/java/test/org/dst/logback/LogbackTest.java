package test.org.dst.logback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    private static final Logger logger = LoggerFactory.getLogger(LogbackTest.class.getName());

    @Test
    public void testLogback() {
        logger.info("This is info message");
        logger.debug("This is debug message");
        logger.error("This is error message");
        logger.trace("This is trace message");
        logger.warn("This is warn message");
    }

}
