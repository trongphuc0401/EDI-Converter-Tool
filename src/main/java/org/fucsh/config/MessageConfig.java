package org.fucsh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MessageConfig {
    private Map<String, MessageTypeConfig> messageTypes;

    public static class MessageTypeConfig {
        private String xsltToXml;
        private String xsltToEdi;
        private String[] mandatorySegments;

        public String getXsltToXml() {
            return xsltToXml;
        }

        public String getXsltToEdi() {
            return xsltToEdi;
        }

        public String[] getMandatorySegments() {
            return mandatorySegments;
        }

        public void setMandatorySegments(String[] mandatorySegments) {
            this.mandatorySegments = mandatorySegments;
        }
    }

    public Map<String, MessageTypeConfig> getMessageTypes() {
        return messageTypes;
    }

    public static MessageConfig loadConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = MessageConfig.class.getClassLoader().getResourceAsStream("config.json");
        return mapper.readValue(is, MessageConfig.class);
    }
}
