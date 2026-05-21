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

    @Test
    void givenIncorrectNumber_whenFindByNumber_thenReturnNull() {
        pb.add("Name", "555");
        String result = pb.findByNumber("777");

        Assertions.assertNull(result);
    }

    @Test
    void givenName_whenFindByName_thenReturnNumber() {
        String name = "Nines and AKs";
        String number = "911";

        pb.add(name, number);
        String result = pb.findByName(name);

        Assertions.assertEquals(number, result);
    }

    @Test
    void givenIncorrectName_whenFindByName_thenReturnNull() {
        pb.add("KEKW", "345");
        String result = pb.findByName("LULW");

        Assertions.assertNull(result);
    }
}
