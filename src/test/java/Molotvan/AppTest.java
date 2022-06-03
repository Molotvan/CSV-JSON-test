package Molotvan;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppTest {
    public static List<Employee> data() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(2L, "Peter", "Petrov", "RU", 38));
        employees.add(new Employee(4L, "Michael", "Jackson", "UK", 29));
        return employees;
    }


    @Test
    public void testListToJson() {
        final String expected = "[{\"id\":2,\"firstName\":\"Peter\",\"lastName\":\"Petrov\",\"country\":\"RU\",\"age\":38},{\"id\":4,\"firstName\":\"Michael\",\"lastName\":\"Jackson\",\"country\":\"UK\",\"age\":29}]";
        final String result = Main.listToJson(data());
        assertEquals(expected, result);
    }

    @Test
    public void testWriteString() {
        final String argument2 = "data2.json";
        Main.writeString(data(), argument2);
        assertTrue(new File(argument2).delete());
    }

    @Test
    public void testJsonToList() {
        String argument = Main.listToJson(data());
        final List<Employee> result = Main.jsonToList(argument);
        assertEquals(data(), result);
    }
}

