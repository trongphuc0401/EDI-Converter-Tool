package org.fucsh;

import org.fucsh.config.MessageConfig;
import org.fucsh.parser.EdiParser;
import org.fucsh.transformer.EdiTransformer;
import org.fucsh.validator.EdiValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java -jar edi-converter.jar <input.edi> <output.xml> <messageType>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String messageType = args[2];

        try {
            // Load configuration
            MessageConfig config = MessageConfig.loadConfig();
            MessageConfig.MessageTypeConfig typeConfig = config.getMessageTypes().get(messageType);
            if (typeConfig == null) {
                throw new Exception("Unsupported message type: " + messageType);
            }

            // Read and validate EDI
            String ediContent = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(inputFile)));
            EdiValidator validator = new EdiValidator();
            validator.validate(ediContent, typeConfig);

            // Parse EDI to XML
            EdiParser parser = new EdiParser();
            String intermediateXml = parser.parseToXml(inputFile);

            // Transform to target format
            EdiTransformer transformer = new EdiTransformer();
            transformer.transform(intermediateXml, typeConfig.getXsltToXml(), outputFile);

            logger.info("Conversion completed successfully for {}", messageType);
        } catch (Exception e) {
            logger.error("Conversion failed: {}", e.getMessage(), e);
            System.out.println("Error: " + e.getMessage());
        }
    }
}