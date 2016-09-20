package application.service;


import app.Domain.XACMLrequest;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XACMLparser {

    public static String getDecision(String response) {
        Document doc = readXML(response);
        return doc.getElementsByTagName("Decision").item(0).getTextContent();
    }

   /* public XACMLrequest getRequestParameters() {

    }*/

    private static Document readXML(String response) {
        try {
            Document doc = loadXMLFromString(response);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    private static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }

  /*  public static String getResource(String request_path) {
        byte[] encoded = Files.readAllBytes(Paths.get(request_path));
        String request = new String(encoded, StandardCharsets.UTF_8);
        Document doc = readXML(request);
        return doc.getElementsByTagName("Decision").item(0).getTextContent();
    }*/
}
