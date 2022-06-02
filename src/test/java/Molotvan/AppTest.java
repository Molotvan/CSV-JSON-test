package Molotvan;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class AppTest 
{


//    @BeforeEach

    public static List<Employee> data(){
         List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(2L, "Peter", "Petrov", "RU", 38));
        employees.add(new Employee(4L, "Michael", "Jackson", "UK", 29 ));
    return employees;
    }


    @Test
    public void testListToJson()
    {
        //final List<Employee> argument = employees;
//        argument.add(new Employee(2L, "Peter", "Petrov", "RU", 38 ));
//        argument.add(new Employee(4L, "Michael", "Jackson", "UK", 29 ));
        final String expected = new String("[{\"id\":2,\"firstName\":\"Peter\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":38},{\"id\":4,\"firstName\":\"Michael\",\"lastName\":\"Jackson\",\"country\":\"UK\",\"age\":29}]");
        final String result = Main.listToJson(data());
        assertEquals(expected, result);
    }
     @Test
    public void testWriteString(){
//         final List<Employee> argument = new ArrayList<>();
//         argument.add(new Employee(2L, "Peter", "Petrov", "RU", 38 ));
//         argument.add(new Employee(4L, "Michael", "Jackson", "UK", 29 ));
         final String argument2 = "data2.json";
         Main.writeString(data(), argument2);
         assertTrue(new File(argument2).exists());
             if(new File(argument2).exists()){
             File file = new File(argument2);
             file.delete();
         }
     }
     @Test
     public void testJsonToList(){
//         final List<Employee> employees = new ArrayList<>();
//         employees.add(new Employee(2L, "Peter", "Petrov", "RU", 38 ));
//         employees.add(new Employee(4L, "Michael", "Jackson", "UK", 29 ));
         String argument = Main.listToJson(data());
         final List<Employee> result = Main.jsonToList(argument);
         //final List<Employee> expected = employees;
         assertEquals(data(), result);
     }



}
