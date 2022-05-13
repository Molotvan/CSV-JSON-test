package Molotvan;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class My {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        String[] Employee = "1,John,Smith,USA,25".split(",");
        String[] Employee2 = "2,Ivan,Petrov,RU,23".split(",");

        try(CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true))){
            writer.writeNext(Employee);
            writer.writeNext(Employee2);
        }catch (IOException e){
            e.printStackTrace();
        }
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        List<Employee> list = parseCSV(columnMapping, "data.csv");
        String json = listToJson(list);
        XmlBuilder.createXML();
        writeString(parseXML("data.xml"));

    }

    public static List<Employee> parseCSV( String[] columnMapping, String fileName){
        List<Employee> employees = new ArrayList<>();
        try(CSVReader reader = new CSVReader(new FileReader(fileName))){
                ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
                strategy.setType(Employee.class);
                strategy.setColumnMapping(columnMapping);
                CsvToBean<Employee> employee = new CsvToBeanBuilder<Employee>(reader)
                        .withMappingStrategy(strategy)
                        .build();
                employees = employee.parse();

        }catch (IOException e ){
            e.printStackTrace();
        }
        return employees;
    }

    public static String listToJson(List<Employee> list){
        Type listType = new TypeToken<List<Employee>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }

    public static List<Employee> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));
        List<Employee> employees = new ArrayList<>();

        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                List<String> employeeAtrList = new ArrayList<>();
                NodeList employeeAtr = element.getChildNodes();
                for (int j = 0; j < employeeAtr.getLength(); j++) {
                    Node atr = employeeAtr.item(j);
                    if (Node.ELEMENT_NODE == atr.getNodeType()) {
                        Element atr1 = (Element) atr;
                        employeeAtrList.add(atr1.getFirstChild().getTextContent());
                    }
                }
                employees.add(new Employee(Long.parseLong(employeeAtrList.get(0)), employeeAtrList.get(1), employeeAtrList.get(2), employeeAtrList.get(3),
                        Integer.parseInt(employeeAtrList.get(4))));
            }
        }
        return employees;
    }

    public static void writeString(List<Employee> employees){
        try(FileWriter file = new FileWriter("new_data.json")){
            file.write(listToJson(employees));
            file.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

