package com.cdl.automator;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.cdl.util.Utilities;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class XMLValidatorTest extends Utilities{
    public static final String SCHEMA_FILE = XSD_PATH;
    public static final String XML_FILE = XML_PATH;

    @Test
    public static void test() {
    XMLValidatorTest XMLValidator = new XMLValidatorTest();
    XMLValidator.validate(XML_FILE, SCHEMA_FILE);
        
     /*   if(valid)
        	System.out.printf("XML IS VALID");
        if(!valid)
        	System.out.println("XML IS INVALID");*/
    }

    private boolean validate(String xmlFile, String schemaFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(getResource(schemaFile)));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(getResource(xmlFile))));
            return true;
        } catch (SAXException | IOException e) {
           // e.printStackTrace();
            return false;
        }
    }

    private String getResource(String filename) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        Objects.requireNonNull(resource);

        return resource.getFile();
    }
}