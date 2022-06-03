package Molotvan;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;


public class HamcrestTest {
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
        assertThat(expected, Matchers.equalTo(result));
    }

    @Test
    public void testJsonToList() {
        String argument = Main.listToJson(data());
        final List<Employee> result = Main.jsonToList(argument);
        assertThat(data(), equalTo(result));
    }

    @Test
    public void givenBean_whenHasValue_thenCorrect() {
        Employee person = data().get(1);
        assertThat(person, hasProperty("firstName"));
    }

    @Test
    public void givenBean_whenHasCorrectValue_thenCorrect() {
        Employee person = data().get(1);
        assertThat(person, hasProperty("country", equalTo("UK")));
    }
}

