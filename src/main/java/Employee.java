public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id +
                ", firstName=\'" + firstName + '\'' +
                ", lastName=\'" + lastName + '\'' +
                ", country=\'" + country + '\'' +
                ", age=" + age + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Employee comp = (Employee) obj;
        if (comp.id == this.id &&
            comp.firstName.equals(this.firstName) &&
            comp.lastName.equals(this.lastName) &&
            comp.country.equals(this.country) &&
            comp.age == this.age)
        {
            return true;
        }
        return false;
    }
}