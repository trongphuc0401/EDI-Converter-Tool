package org.fucsh.validator;

import org.fucsh.config.MessageConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdiValidatorTest {
    private EdiValidator validator;
    private MessageConfig.MessageTypeConfig config;

    @BeforeEach
    void setUp() {
        validator = new EdiValidator();
        config = new MessageConfig.MessageTypeConfig();
        config.setMandatorySegments(new String[]{"UNB", "UNH", "BGM", "UNT", "UNZ"});
    }
    @Test
    void testValidEdi() {
        String edi = "UNA:+.? '\n" +
                "UNB+UNOC:3+1234567890123:14+9876543210987:14+250513:1430+INV001++1'\n" +
                "UNH+000000001+INVOIC:D:96A:UN'\n" +
                "BGM+380+INV12345+9'\n" +
                "UNT+3+000000001'\n" +
                "UNZ+1+INV001'";
        assertDoesNotThrow(() -> validator.validate(edi, config));
    }

    @Test
    void testMissingMandatorySegment() {
        String edi = "UNA:+.? '\n" +
                "UNB+UNOC:3+1234567890123:14+9876543210987:14+250513:1430+INV001++1'\n" +
                "UNH+000000001+INVOIC:D:96A:UN'\n" +
                "UNT+2+000000001'\n" +
                "UNZ+1+INV001'";
        Exception exception = assertThrows(Exception.class, () -> validator.validate(edi, config));
        assertTrue(exception.getMessage().contains("Missing mandatory segment: BGM"));
    }
}