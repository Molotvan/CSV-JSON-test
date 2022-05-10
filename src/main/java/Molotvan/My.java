package Molotvan;





import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class My{
    public static void main( String[] args ) {
        String[] Employee = "1,John,Smith,USA,25".split(",");
        String[] Employee2 = "2,Ivan,Petrov,RU,23".split(",");

        try(CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true))){
            writer.writeNext(Employee);
            writer.writeNext(Employee2);
        }catch (IOException e){
            e.printStackTrace();
        }
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        list.forEach(System.out::println);
        String json = listToJson(list);
        System.out.println(list);
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
        //list.forEach(gson::toJson);
        return gson.toJson(list, listType);
    }
}
