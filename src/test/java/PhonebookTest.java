import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PhonebookTest {

    static ByteArrayOutputStream testOut = null;

    Phonebook pb = null;

    @BeforeAll
    static void firstSetup() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @BeforeEach
    void startup() {
        pb = new Phonebook();
        testOut.reset();
    }

    @AfterAll
    static void finalCleanUp() {
        System.setOut(System.out); // kinda bad, because mb before tests setOut was non-default, but idk how to getOut()
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

    @Test
    void givenNonEmptyPhonebook_whenPrintAllNames_thenOutputAllNamesAlphabetically() {
        String name1 = "Anna";
        String name2 = "Bill";
        String name3 = "Claire";

        pb.add(name1, "334");
        pb.add(name2, "543");
        pb.add(name3, "2932");

        String expected = "Anna, Bill, Claire" + System.lineSeparator();
        pb.printAllNames();

        Assertions.assertEquals(expected, testOut.toString());
    }

    @Test
    void givenEmptyPhonebook_whenPrintAllNames_thenOutputEmptyString() {
        pb.printAllNames();

        Assertions.assertEquals(System.lineSeparator(), testOut.toString());
    }


}
