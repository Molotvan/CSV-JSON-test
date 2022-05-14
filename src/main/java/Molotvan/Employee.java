package Molotvan;

public class Employee {
    public Long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {

    }

    public Employee(Long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{"+"id='" + id  + '\'' +
                "firstname='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "country='" + country + '\'' +
                "age='" + age + '\'' +
                '}';
    }
}
