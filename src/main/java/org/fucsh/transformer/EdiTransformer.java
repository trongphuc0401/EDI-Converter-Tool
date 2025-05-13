package org.fucsh.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import java.io.*;

public class EdiTransformer {
    private static final Logger logger = LogManager.getLogger(EdiTransformer.class);

    public void transform(String xmlContent, String xsltFile, String outputFile) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFile)));

        StringReader xmlReader = new StringReader(xmlContent);
        SAXSource saxSource = new SAXSource(new InputSource(xmlReader));
        StreamResult result = new StreamResult(new File(outputFile));

        transformer.transform(saxSource, result);
        logger.info("Transformation completed: {}", outputFile);
    }
}
