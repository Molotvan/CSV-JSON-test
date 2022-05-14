package Molotvan;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLBuilder {
    public static void writeXML() throws ParserConfigurationException, TransformerException {


    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();

    Element root = document.createElement("stuff");
    document.appendChild(root);
    Element employee = document.createElement("employee");
    root.appendChild(employee);
    Element id  = document.createElement("id");
    id.setNodeValue("1");
    employee.appendChild(id);
    Element firstName  = document.createElement("firstName");
    firstName.setNodeValue("John");
    employee.appendChild(firstName);
    Element lastName  = document.createElement("lastName");
    lastName.setNodeValue("Smith");
    employee.appendChild(lastName);
    Element country  = document.createElement("country");
    country.setNodeValue("USA");
    employee.appendChild(country);
    Element age  = document.createElement("age");
    age.setNodeValue("25");
    employee.appendChild(age);
    Element employee2 = document.createElement("employee");
        root.appendChild(employee2);
        Element id2  = document.createElement("id");
        id2.setNodeValue("2");
        employee2.appendChild(id2);
        Element firstName2 = document.createElement("firstName");
        firstName2.setNodeValue("Ivan");
        employee2.appendChild(firstName2);
        Element lastName2  = document.createElement("lastName");
        lastName2.setNodeValue("Petrov");
        employee2.appendChild(lastName2);
        Element country2  = document.createElement("country");
        country2.setNodeValue("RU");
        employee2.appendChild(country2);
        Element age2  = document.createElement("age");
        age2.setNodeValue("32");
        employee2.appendChild(age2);

    DOMSource domSource = new DOMSource(document);
    StreamResult streamResult = new StreamResult(new File("data.xml"));
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.transform(domSource, streamResult);
    }
}
