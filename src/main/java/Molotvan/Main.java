package Molotvan;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
public class Main
{
    public static void main( String[] args ) {
        String[] Employee = "1,John,Smith,USA,25".split(",");
        String[] Employee2 = "2,Inav,Petrov,RU,23".split(",");

        try(CSVWriter writer = new CSVWriter(new FileWriter("data.csv", true))){
            writer.writeNext(Employee);
            writer.writeNext(Employee2);
        }catch (IOException e){
            e.printStackTrace();
        }
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);

    }
    public static List<Employee> parseCSV( String[] columnMapping, String fileName){
        try(CSVReader reader = new CSVReader(new FileReader("data.csv"))){
                ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
                strategy.setType(Employee.class);
                strategy.setColumnMapping(columnMapping);
                CsvToBean<Employee> employee = new CsvToBeanBuilder<Employee>(reader)
                        .withMappingStrategy(strategy)
                        .build();
                List<Employee> employees = employee.parse();

        }catch (IOException e ){
            e.printStackTrace();
        }
        return employees;



    }
}
