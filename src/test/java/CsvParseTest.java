import org.junit.jupiter.api.*;
import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CsvParseTest {
    static File csvTestFilesFolder = new File(Utils.basePath, "csv");

    @BeforeAll
    static void prepare() {
        csvTestFilesFolder.mkdirs();
    }

    @AfterAll
    static void finishUp() {
        Utils.deleteFolderAndFiles(csvTestFilesFolder);
    }

    @Test
    void givenCsv_whenParse_thenReturnEmployeeList() {
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String testFileName = "csvDataTest1.csv";
        final String testData = "1,John,Smith,USA,25\n2,Inav,Petrov,RU,23";
        List<Employee> expectedOutput = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        File filePath = new File(csvTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        List<Employee> result = Main.parseCSV(columnMapping, filePath);

       Assertions.assertEquals(result, expectedOutput);
    }
    @Test
    void givenCsv_whenParse_thenReturnEmployeeList_Hamcrest() {
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String testFileName = "csvDataTest2.csv";
        final String testData = "1,John,Smith,USA,25\n2,Inav,Petrov,RU,23";
        List<Employee> expectedOutput = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        File filePath = new File(csvTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        List<Employee> result = Main.parseCSV(columnMapping, filePath);

        assertThat(result, equalTo(expectedOutput));
    }

    @Test
    void givenCsv_whenParse_thenReturnNonEmptyList_Hamcrest() {
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String testFileName = "csvDataTest2.csv";
        final String testData = "1,John,Smith,USA,25\n2,Inav,Petrov,RU,23";
        List<Employee> expectedOutput = List.of(
                new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Inav", "Petrov", "RU", 23)
        );
        File filePath = new File(csvTestFilesFolder, testFileName);
        Utils.createFileAndAddData(filePath, testData);

        List<Employee> result = Main.parseCSV(columnMapping, filePath);

        assertThat(result, not(emptyIterable()));
    }
}