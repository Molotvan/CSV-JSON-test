package Molotvan;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", "Smith", "USA", 23));
        employees.add(new Employee(2L, "Ivan", "Petrov", "RU", 35));
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        writeCsv(columnMapping, employees, "data.csv");
        List<Employee> list = parseCSV(columnMapping, "data.csv");
        writeString(list, "data.json");
        XmlBuilder.createXML();
        writeString(parseXML("data.xml"), "new_data.json");
        System.out.println(jsonToList(readString("new_data.json")));
        System.out.println(listToJson(employees));
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> employees = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> employee = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            employees = employee.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {}.getType();
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

    public static void writeString(List<Employee> employees, String jsonFileName) {
        try (FileWriter file = new FileWriter(jsonFileName)) {
            file.write(listToJson(employees));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readString(String jsonFileName) {
        String json = new String();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFileName))) {
            json = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return json;
    }

    public static List<Employee> jsonToList(String json) {
        List<Employee> employees = new ArrayList<>();
        JsonParser parser = new JsonParser();
        Object object = parser.parse(json);
        JsonArray jsEmployees = (JsonArray) object;

        for (JsonElement jsEmployee : jsEmployees) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Employee employee = new Employee();
            employee = gson.fromJson(jsEmployee, employee.getClass());
            employees.add(employee);
        }
        return employees;
    }

    public static void writeCsv(String[] columnMapping, List<Employee> employees, String csvFileName) {
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);
        try (Writer writer = new FileWriter(csvFileName)) {
            StatefulBeanToCsv<Employee> csv = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .withMappingStrategy(strategy)
                    .build();
            csv.write(employees);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

}

