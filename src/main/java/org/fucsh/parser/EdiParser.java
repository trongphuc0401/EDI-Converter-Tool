package org.fucsh.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;

public class EdiParser {
    private static final Logger logger = LogManager.getLogger(EdiParser.class);

    public String parseToXml(String ediFile) throws Exception {
        StringBuilder xml = new StringBuilder("<EDIFACT>");
        try (BufferedReader reader = new BufferedReader(new FileReader(ediFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String segmentName = line.substring(0, 3);
                xml.append("<Segment name=\"").append(segmentName).append("\">")
                        .append(line).append("</Segment>");
            }
        }
        xml.append("</EDIFACT>");
        logger.debug("Intermediate XML generated: {}", xml);
        return xml.toString();
    }
}
