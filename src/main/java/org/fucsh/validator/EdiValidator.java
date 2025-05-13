package org.fucsh.validator;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fucsh.config.MessageConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EdiValidator {
    private static final Logger logger = LogManager.getLogger(EdiValidator.class);

    public void validate(String ediContent, MessageConfig.MessageTypeConfig config) throws Exception {
        String[] lines = ediContent.split("'");
        int segmentCount = 0;
        String unhRef = null, untRef = null;
        String unbRef = null, unzRef = null;
        int messageCount = 0;
        Set<String> foundSegments = new HashSet<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            segmentCount++;
            String[] parts = line.split("\\+");
            String segmentName = parts[0];
            foundSegments.add(segmentName);

            if ("UNB".equals(segmentName)) {
                unbRef = parts[5].split(":")[0];
            } else if ("UNH".equals(segmentName)) {
                unhRef = parts[1];
                messageCount++;
            } else if ("UNT".equals(segmentName)) {
                int reportedCount = Integer.parseInt(parts[1]);
                untRef = parts[2];
                if (!unhRef.equals(untRef)) {
                    throw new Exception("UNH/UNT reference mismatch: " + unhRef + " vs " + untRef);
                }
                unhRef = null;
            } else if ("UNZ".equals(segmentName)) {
                int reportedMessageCount = Integer.parseInt(parts[1]);
                unzRef = parts[2];
                if (!unbRef.equals(unzRef)) {
                    throw new Exception("UNB/UNZ reference mismatch: " + unbRef + " vs " + unzRef);
                }
                if (reportedMessageCount != messageCount) {
                    throw new Exception("UNZ message count mismatch: " + reportedMessageCount + " vs " + messageCount);
                }
            }
        }

        // Check mandatory segments
        for (String mandatorySegment : config.getMandatorySegments()) {
            if (!foundSegments.contains(mandatorySegment)) {
                throw new Exception("Missing mandatory segment: " + mandatorySegment);
            }
        }

        logger.info("Validation passed. Segments: {}, Messages: {}", segmentCount, messageCount);
    }
}
