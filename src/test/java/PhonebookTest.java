import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PhonebookTest {

    static Phonebook pb = null;

    @BeforeEach
    void startup() {
        pb = new Phonebook();
    }

    @Test
    void givenNameAndNumber_whenAdd_thenReturnNumberOfContacts() {
        int result = pb.add("Name", "88005553535");
        Assertions.assertEquals(1, result);
    }

    @Test
    void givenNumber_whenFindByNumber_thenReturnCorrectName() {
        String name = "Name";
        String number = "88005553535";

        pb.add(name, number);
        String result = pb.findByNumber(number);

        Assertions.assertEquals(name, result);

    }
}
